package com.bryan.cloud.controller;

import com.bryan.cloud.apis.PayFeignSentinelApi;
import com.bryan.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderNacosController {
    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-serivce}")
    private String serviceURL;

    @Resource
    private PayFeignSentinelApi payFeignSentinelApi;

    @GetMapping("/consumer/pay/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        String result = restTemplate.getForObject(serviceURL + "/pay/nacos/" + id, String.class);
        return result + "\t" + "我是OrderNacosController8100调用者";
    }

    /**
     * Sentinel+OpenFeign整合
     * @param orderNo
     * @return
     */
    @GetMapping("/consumer/pay/nacos/get/{orderNo}")
    public ResultData getPayByOrderNo(@PathVariable("orderNo") String orderNo) {
        return payFeignSentinelApi.getPayByOrderNo(orderNo);
    }
}
