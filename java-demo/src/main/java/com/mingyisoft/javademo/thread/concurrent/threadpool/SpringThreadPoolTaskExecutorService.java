package com.mingyisoft.javademo.thread.concurrent.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SpringThreadPoolTaskExecutorService {
    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Async
    public void processAsyncTask() {
        System.out.println("我爱你，亲爱的姑娘～～～");
    }
}
