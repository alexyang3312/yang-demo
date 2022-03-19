package com.mingyisoft.demo.provider;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SentinelController {
    private static AtomicInteger pass = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();

    @GetMapping("/sentinel-demo")
    public String sentinelDemo() {
        Entry entry = null;
        try {
            entry = SphU.entry("sentinel-demo", EntryType.IN);
            pass.incrementAndGet();
//          System.out.println("entry.getCurNode().curThreadNum()===>"+entry.getCurNode().curThreadNum());

            try {
                Thread.sleep(5 * 1000);
                // 模拟流量先降低，一段时间后再增高，一段时间后再下降
//                if(total.get()>=0 && total.get()<=10000){
//                    TimeUnit.MILLISECONDS.sleep(200);
//                } else if(total.get()>=100000 && total.get()<=200000){
//                    TimeUnit.MILLISECONDS.sleep(2);
//                } else {
//                    TimeUnit.MILLISECONDS.sleep(20);
//                }
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

        return "ok...";
    }
}
