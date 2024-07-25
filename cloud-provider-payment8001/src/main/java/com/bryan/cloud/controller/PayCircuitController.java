package com.bryan.cloud.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayCircuitController {
    @GetMapping("/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("-------circuit id不能为负数");
        }

        if (id == 9999) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Hello, circuit. inputId: " + id + "\t" + IdUtil.simpleUUID();
    }

    @GetMapping("/pay/bulkhead/{id}")
    public String myCircuitBulkhead(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("-------bulkhead id不能为负数");
        }

        if (id == 9999) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Hello, bulkhead. inputId: " + id + "\t" + IdUtil.simpleUUID();
    }

    @GetMapping("/pay/ratelimit/{id}")
    public String myRateLimit(@PathVariable("id") Integer id) {
        return "Hello, rateLimit, inputId" + id + "\t" + IdUtil.simpleUUID();
    }
}
