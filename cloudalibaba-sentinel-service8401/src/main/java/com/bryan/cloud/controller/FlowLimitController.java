package com.bryan.cloud.controller;

import com.bryan.cloud.service.FlowLimitService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {
    @Resource
    private FlowLimitService flowLimitService;

    @GetMapping("/testA")
    public String testA() {
        return "--------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "--------testB";
    }

    /**
     * 流控模式：链路
     * @return
     */
    @GetMapping("/testC")
    public String testC() {
        flowLimitService.common();
        return "---------testC";
    }

    /**
     * 流控模式：链路
     * @return
     */
    @GetMapping("/testD")
    public String testD() {
        flowLimitService.common();
        return "---------testD";
    }

    /**
     * 流控效果：排队等待
     * @return
     */
    @GetMapping("/testE")
    public String testE() {
        System.out.println(System.currentTimeMillis() + " , testE排队等待");
        return "----------testE";
    }

    /**
     * 熔断规则：慢比例调用
     * @return
     */
    @GetMapping("/testF")
    public String testF() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------- 测试熔断规则：慢调用比例");
        return "-------- testF 测试熔断规则：慢调用比例";
    }

    /**
     * 熔断规则：异常比例
     * @return
     */
    @GetMapping("/testG")
    public String testG() {
        System.out.println("--------- 测试熔断规则：异常比例");
        int tmp = 1 / 0;
        return "--------- testG 测试熔断规则：异常比例";
    }

    /**
     * 熔断规则：异常数
     * @return
     */
    @GetMapping("/testH")
    public String testH() {
        System.out.println("--------- 测试熔断规则：异常数");
        int tmp = 1 / 0;
        return "--------- testH 测试熔断规则：异常数";
    }
}
