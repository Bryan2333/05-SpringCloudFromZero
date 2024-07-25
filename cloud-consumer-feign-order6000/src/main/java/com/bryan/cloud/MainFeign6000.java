package com.bryan.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 向consul注册中心注册服务
@EnableFeignClients    // 启动feign客户端，定义服务+端口
public class MainFeign6000 {
    public static void main(String[] args) {
        SpringApplication.run(MainFeign6000.class, args);
    }
}
