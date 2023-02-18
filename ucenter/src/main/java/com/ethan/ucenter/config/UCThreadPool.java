package com.ethan.ucenter.config;

import com.ethan.common.response.ResponseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Ethan 2023/2/12
 */

@Configuration
@EnableAsync
public class UCThreadPool {

    public static AsyncResult<ResponseResult> asyncResultWrapper(ResponseResult responseResult) {
        return new AsyncResult<>(responseResult);
    }

    /**
     * 暂定名 A 池
     */
    @Bean("APool")
    public static Executor APool() {
        // Spring 里的线程池创建方式（普通的用 ThreadPoolExecutor）
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(5);
        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 允许线程的空闲时间60秒：非核心线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("A-");
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
