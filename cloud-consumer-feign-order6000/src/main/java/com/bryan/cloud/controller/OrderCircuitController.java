package com.bryan.cloud.controller;

import com.bryan.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/feign/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id) {
        return payFeignApi.myCircuit(id);
    }

    // CircuitBreaker服务降级后的回滚方法
    public String myCircuitFallback(Integer id, Throwable t) {
        return "系统繁忙，请稍候尝试……";
    }

    // 信号量隔离
    @GetMapping("/feign/pay/bulkheadsemaphore/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myCircuitBulkhead", type = Bulkhead.Type.SEMAPHORE)
    public String myCircuitBulkheadSemaphore(@PathVariable("id") Integer id) {
        return payFeignApi.myBulkhead(id) + "\t" + "Semaphore";
    }

    public String myCircuitBulkheadSemaphore(Throwable t) {
        return "隔板超出最大限制，系统繁忙，请稍候尝试……" + "\t" + "Semaphore";
    }

    // 线程池隔离
    @GetMapping("/feign/pay/bulkheadthreadpool/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myCircuitBulkheadThread", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> myCircuitBulkheadThread(@PathVariable("id") Integer id) {
        System.out.println(Thread.currentThread().getName() + "\t" + "----- 开始进入");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "----- 准备离开");
        return CompletableFuture.supplyAsync(() -> payFeignApi.myBulkhead(id) + "\t" + "ThreadPool");
    }

    public CompletableFuture<String> myCircuitBulkheadThread(Throwable t) {
        return CompletableFuture.supplyAsync(() -> "隔板超出最大限制，系统繁忙，请稍候尝试……" + "\t" + "ThreadPool");
    }

    // 限流器
    @GetMapping("/feign/pay/ratelimit/{id}")
    @RateLimiter(name = "cloud-payment-service", fallbackMethod = "myRateLimitFallback")
    public String myRateLimit(@PathVariable("id") Integer id) {
        return payFeignApi.myRatelimit(id);
    }

    public String myRateLimitFallback(Throwable t) {
        return "你被限流了，禁止访问……";
    }
}
