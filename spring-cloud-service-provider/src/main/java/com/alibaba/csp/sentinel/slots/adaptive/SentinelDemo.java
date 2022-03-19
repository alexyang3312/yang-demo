package com.alibaba.csp.sentinel.slots.adaptive;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SentinelDemo {
    private static AtomicInteger pass = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();

    private static volatile boolean stop = false;
    private static final int threadCount = 100;
    private static int seconds = 60 + 40;

    public static void main(String[] args) throws Exception {
        tick();
        initSystemRule();

        for (int i = 0; i < threadCount; i++) {
            Thread entryThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Entry entry = null;
                        try {
                            // 进行声明资源
                            // 在设置好限流规则后会进入到SphU.entry方法中，通过创建slot链调用到SystemSlot，这里是系统自适应限流的地方。
                            entry = SphU.entry("methodA", EntryType.IN);

                            pass.incrementAndGet();

//                            System.out.println("entry.getCurNode().curThreadNum()===>"+entry.getCurNode().curThreadNum());

                            try {
                                // 模拟流量先降低，一段时间后再增高，一段时间后再下降
                                if(total.get()>=0 && total.get()<=10000){
                                    TimeUnit.MILLISECONDS.sleep(200);
                                } else if(total.get()>=100000 && total.get()<=200000){
                                    TimeUnit.MILLISECONDS.sleep(2);
                                } else {
                                    TimeUnit.MILLISECONDS.sleep(20);
                                }
                            } catch (InterruptedException e) {
                                // ignore
                            }
                        } catch (BlockException e1) {
                            block.incrementAndGet();
                            try {
                                TimeUnit.MILLISECONDS.sleep(20);
                            } catch (InterruptedException e) {
                                // ignore
                            }
                        } catch (Exception e2) {
                            // biz exception
                        } finally {
                            total.incrementAndGet();
                            if (entry != null) {
                                entry.exit();
                            }
                        }
                    }
                }

            });
            entryThread.setName("working-thread");
            entryThread.start();
        }
    }

    private static void initSystemRule() {
        List<SystemRule> rules = new ArrayList<SystemRule>();
        SystemRule rule = new SystemRule();
        // max load is 3 限制最大负载
//        rule.setHighestSystemLoad(3);
        // max cpu usage is 60% cpu负载60%
//        rule.setHighestCpuUsage(0.6);
        // max avg rt of all request is 10 ms
//        rule.setAvgRt(10);
        // max total qps is 20
        rule.setQps(2000);
        // max parallel working thread is 10
//        rule.setMaxThread(5000);

        rules.add(rule);
        SystemRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void tick() {
        Thread timer = new Thread(new TimerTask());
        timer.setName("sentinel-timer-task");
        timer.start();
    }

    static class TimerTask implements Runnable {
        @Override
        public void run() {
            System.out.println("begin to statistic!!!");
            long oldTotal = 0;
            long oldPass = 0;
            long oldBlock = 0;
            while (!stop) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                long globalTotal = total.get();
                long oneSecondTotal = globalTotal - oldTotal;

                oldTotal = globalTotal;

                long globalPass = pass.get();
                long oneSecondPass = globalPass - oldPass;
                oldPass = globalPass;

                long globalBlock = block.get();
                long oneSecondBlock = globalBlock - oldBlock;
                oldBlock = globalBlock;

                System.out.println(seconds + ", " + TimeUtil.currentTimeMillis() + ", total:"
                        + oneSecondTotal + ", pass:"
                        + oneSecondPass + ", block:" + oneSecondBlock +"======globalTotal======"+ globalTotal + "-----CurrentSystemAvgLoad-----"
                        + SystemRuleManager.getCurrentSystemAvgLoad() + "-----CurrentCpuUsage-----" + SystemRuleManager.getCurrentCpuUsage() + "------MaxThreadThreshold-----"+SystemRuleManager.getMaxThreadThreshold());



                if (seconds-- <= 0) {
                    stop = true;
                }
            }
            System.exit(0);
        }
    }
}
