package com.bryan.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayAlibabaController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/pay/nacos/{id}")
    public String getPayInfo(@PathVariable("id") Integer id) {
        return "Nacos registry, server port: " + serverPort + ", id: " + id;
    }
}
