package com.alibaba.csp.sentinel.slots.adaptive;

import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemStatusListener;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdaptiveSlot extends AbstractLinkedProcessorSlot<DefaultNode> {
    /**
     * System Load 阈值
     */
    public static float MAX_SYSTEM_LOAD = 4.5f;

    /**
     * 初始化流控阈值
     */
    public static double DEFAULT_COUNT = 10;

    /**
     * 规则更新频率（ms)
     */
    public static long RULE_UPDATE_WINDOW = 1000;

    /**
     * 熔断降级后的流量
     */
    public static int DEGRADE_FLOW = 1;

    /**
     * 资源流控阈值最小值
     */
    public static int RESOURCE_MIN_FLOW = 10;

    public static double DEGRADE_RT = 4900;

    public static int DEGRADE_TIME_WINDOW = 5;


    /**
     * 流控规则
     */
    private static Map<String, FlowRule> rules = new ConcurrentHashMap<String, FlowRule>();

    private static Map<String,Node> nodes =new ConcurrentHashMap<String,Node>();

    /**
     * 熔断资源计时器
     */
    private static Map<String, Integer> degradeTimer = new ConcurrentHashMap<String, Integer>();

    private static double currentSystemLoad;

    /**
     * 系统状态
     */
    private static AtomicBoolean updating = new AtomicBoolean(false);
    private static long latestUpdateTime;

    private static SystemStatusListener statusListener = null;

    @SuppressWarnings("PMD.ThreadPoolCreationRule")
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("sentinel-automatic-status-record-task", true));

    static {
        statusListener = new SystemStatusListener();
        scheduler.scheduleAtFixedRate(statusListener, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode node, int count,
                      boolean prioritized, Object... args) throws Throwable {
        List<FlowRule> rulesA = new ArrayList<FlowRule>();
        String resourceName = resourceWrapper.getName();
        FlowRule rule = new FlowRule(resourceName);
        // max load is 3 限制最大负载
//        rule.setHighestSystemLoad(3);
        // max cpu usage is 60% cpu负载60%
//        rule.setHighestCpuUsage(0.6);
        // max avg rt of all request is 10 ms
//        rule.setAvgRt(10);
        // max total qps is 20
        rule.setCount(10);
        // max parallel working thread is 10
//        rule.setMaxThread(5000);

//        rulesA.add(rule);
//        rules.put(resourceName, rule);
//        SystemRuleManager.loadRules(Collections.singletonList(rule));
//        FlowRuleManager.loadRules(rulesA);

//        checkFlow(resourceWrapper, context, node, count, prioritized);
        fireEntry(context, resourceWrapper, node, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        fireExit(context, resourceWrapper, count, args);
    }

    /**
     * 在 Sentinel 加入了一个 AutomaticSlot 类作为动态限流的入口，
     * 分别调用 AutomaticRuleManager 类中的 checkFlow 和 update 方法。
     * 类 TwoPhaseSimplex 能够根据参数矩阵求解单纯形表，出自《算法》第四版的两阶段单纯形算法。
     * checkFlow 为每个资源在第一次被请求时创建一个 FlowRule 对象，之后根据规则检查资源的请求是否符合流控规则。
     *
     * update 根据监控数据更新流控规则，每秒更新一次。
     * 它的功能统计监控数据以及计时器，每当有新的请求检查距离上次更新是否超过 1s 如果是则调用 updateRulesByQPS 方法再次更新规则。
     */
    public void checkFlow(ResourceWrapper resource, Context context, DefaultNode node, int count, boolean prioritized)
            throws BlockException {

        /**
         * 统计监控数据
         * 每秒更新一次流控规则
         */
        if(nodes.get(resource.getName())==null){
            nodes.put(resource.getName(),node);
        }

        // 每秒更新一次 rules
        if (System.currentTimeMillis() - latestUpdateTime < RULE_UPDATE_WINDOW) {
            return;
        }

        if (updating.compareAndSet(false, true)) {
            currentSystemLoad = statusListener.getSystemAverageLoad();

            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            System.out.println("内存---------"+osBean.getTotalPhysicalMemorySize());
            System.out.println("内存---------"+osBean.getCommittedVirtualMemorySize());
            System.out.println("内存---------"+osBean.getFreePhysicalMemorySize());

            System.out.println("CPU: "+statusListener.getCpuUsage());
            System.out.println("Load: "+statusListener.getSystemAverageLoad());

            latestUpdateTime = System.currentTimeMillis();

            updateRulesByQps();

            updating.set(false);

        }

        String resourceName = resource.getName();

        FlowRule rule;
        // 找出当前资源的 rule，如果不存在则创建
        if (rules.get(resourceName) == null) {
            rule = new FlowRule(resourceName);
            // 设置初始值
            rule.setCount(DEFAULT_COUNT);
            rule.setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//            rule.setRater(new DefaultController(rule.getCount(), rule.getGrade()));
            rules.put(resourceName, rule);
            degradeTimer.put(resourceName, 0);
        } else {
            rule = rules.get(resourceName);
        }

//        boolean canPass = rule.getRater().canPass(context.getCurNode(), count);
//        if (!canPass) {
//            throw new FlowException(rule.getLimitApp(), rule);
//        }
    }

    /**
     * 判断各资源的状态并计算出流控阈值
     *
     * 判断上文中讨论的两种情况进行更新，
     * 首先计算 currentUseage = ∑XiCi和useageLevel = currentUseage / maxUseage。
     * 如果 useageLevel 大于 1 则说明当前流量超出了系统的处理能力。
     */
    private void updateRulesByQps() {

        // 分两类情况：1. 当前总流量超出最大值 2.当前总流量未超过最大值
        // 使用minRT作为负载系数来计算服务给系统造成的负载

        Set<String> resourceNameSet = rules.keySet();

        // 将正常状态的资源加入规则计算的集合，异常的资源流量降级

        // 熔断降级
        // 判断是否有资源处于异常需要被降级
        TreeSet<String> activeResources = new TreeSet<String>();
        for (String resource : resourceNameSet) {
            Node node = nodes.get(resource);
            if (node.avgRt() > DEGRADE_RT && degradeTimer.get(resource) == 0) {
                degradeTimer.put(resource, DEGRADE_TIME_WINDOW);
            }
        }

        for (String resource : resourceNameSet) {
            if (degradeTimer.get(resource) == 0) {
                activeResources.add(resource);
            } else {
                rules.put(resource, rules.get(resource).setCount(DEGRADE_FLOW));
                degradeTimer.put(resource, degradeTimer.get(resource) - 1);
            }
        }

        // 计算系统负载

        double otherAppLoad = 0;
        double currentAppLoad = 0;
        for (String resource : activeResources) {
            Node node = nodes.get(resource);
            // 系统其他应用使用的负载 ( otherAppLoad =  totalSystemLoad - sum(minRT*passedQPS) )
            otherAppLoad += node.previousPassQps()*node.minRt()*0.001;
            // 杨：minRtMin换成了minRt

            // 当前流量需要的负载 ( currentAppLoad = sum(minRT*totalQps) )
            currentAppLoad += (node.previousPassQps()+node.previousBlockQps())*node.minRt()*0.001;

            // 杨：minRtMin换成了minRt
        }



        otherAppLoad = currentSystemLoad - otherAppLoad;

        // 当前可用的负载 ( availableLoad = maxLoad - otherAppLoad )
//        double availableLoad =  3*statusListener.getAvailableProcessors() - otherAppLoad;
        // 杨：换了

        double availableLoad = 3 * statusListener.getCpuUsage() - otherAppLoad;

        double loadLevel = currentAppLoad / availableLoad;

        System.out.println("currentAppLoad: "+currentAppLoad);
        System.out.println("availableLoad: "+availableLoad);


        // 计算流控阈值
        // 1. 系统能够处理所有请求时：根据各服务流量比例分配阈值
        if (loadLevel < 1) {
            for (String resourceName : activeResources) {
                Node node = nodes.get(resourceName);
                FlowRule rule = rules.get(resourceName);
                double currentQps = node.previousPassQps();
                double maximumQps = currentQps / loadLevel;
                if(maximumQps< RESOURCE_MIN_FLOW){
                    maximumQps = RESOURCE_MIN_FLOW;
                }
                rule.setCount(maximumQps);
                rules.put(resourceName, rule);
            }
        }
        // 2. 请求数超过系统处理能力时（流量洪峰）：在保护其他服务正常访问的前提下尽可能将流量分配到发生洪峰的服务
        else {
            double[] maxQps = resolve(activeResources);
            for (int i = 0; i<maxQps.length;i++){
                if(maxQps[i]< RESOURCE_MIN_FLOW){
                    maxQps[i] = RESOURCE_MIN_FLOW;
                }
            }

            int i = 0;
            for (String resourceName : activeResources) {
                rules.put(resourceName, rules.get(resourceName).setCount(maxQps[i]));
                i += 1;
            }

        }
        updateRaters();
    }

    /**
     * 根据资源生成单纯形表并求解线性规划问题
     *
     * @param activeResources 待计算流控阈值的资源
     * @return 各资源流控阈值
     */
    private double[] resolve(Set<String> activeResources) {

        int resourceNum = activeResources.size();

        Set<String> resourceNameSet = activeResources;

        // 价值系数 C
        double[] c = new double[resourceNum * 4 + 1];

        for (int i = 0; i < resourceNum; i++) {
            c[i] = 1;
        }
        for (int i = resourceNum * 2 + 1; i < resourceNum * 3 + 1; i++) {
            c[i] = -500;
        }

        // 资源常数矩阵 B
        // 最大值约束
        double[] qps = new double[resourceNum];

        int i = 0;
        for (String resourceName : resourceNameSet) {
            Node node = nodes.get(resourceName);
            qps[i] = node.previousBlockQps()+node.previousPassQps();
            i += 1;
        }

        // 最小值约束
        double[] b = new double[resourceNum * 2 + 1];
        b[0] = 1;
        for (int j = 0; j < resourceNum; j++) {
            b[j + 1] = RESOURCE_MIN_FLOW;
            b[j + 1 + resourceNum] = qps[j];
        }


        // tableaux A
        double[][] a = new double[b.length][c.length];
        // 第一行：机器性能约束
        i = 0;
        for (String resourceName : resourceNameSet) {
            Node node = nodes.get(resourceName);
            a[0][i] = node.minRt() * 0.001;
            // 杨：minRtMin换成了minRt
            i += 1;
        }
        a[0][2 * resourceNum] = 1;

        // 后 resourceNum 行:最小值约束
        i = 0;
        for (String resourceName : resourceNameSet) {
            a[i + 1][i] = 1;
            a[i + 1][i + resourceNum] = -1;
            a[i + 1][i + resourceNum * 2 + 1] = 1;
            i += 1;
        }

        // 后 resourceNum 行:最大值约束
        i = 0;
        for (String resourceName : resourceNameSet) {
            a[i + resourceNum + 1][i] = 1;
            a[i + resourceNum + 1][i + resourceNum * 3 + 1] = 1;
            i += 1;
        }

        TwoPhaseSimplex lp1 = new TwoPhaseSimplex(a, b, c);

        double[] x = lp1.primal();

        return Arrays.copyOf(x, resourceNum);
    }

    /**
     * 将 FlowRule.count 更新到rater中
     */
    private static void updateRaters() {
        for (String resourceName : rules.keySet()) {
            FlowRule rule = rules.get(resourceName);
//            rule.setRater(new DefaultController(rule.getCount(), rule.getGrade()));
            rules.put(resourceName, rule);
        }
    }
}