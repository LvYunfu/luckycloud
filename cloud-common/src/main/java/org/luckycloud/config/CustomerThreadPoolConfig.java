package org.luckycloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lvyf
 * @description:
 * @date 2024/11/7
 */

@Configuration
public class CustomerThreadPoolConfig {




    @Bean(name = "syncThreadPool")
    public ThreadPoolExecutor aiChatThreadPoolExecutor() {
        int core = Runtime.getRuntime().availableProcessors();
        int corePoolSize = core * 2;
        int maximumPoolSize = core * 4;
        long keepAliveTime = 5000L;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(200);
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "sync-task-" + threadNumber.getAndIncrement());
                // 设为非守护线程，防止主线程结束时任务被强行终止
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                // 统一优先级
                if (t.getPriority() != Thread.NORM_PRIORITY) {
                    t.setPriority(Thread.NORM_PRIORITY);
                }
                return t;
            }
        };

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

}
