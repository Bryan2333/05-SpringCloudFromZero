package com.bryan.cloud.controller;

import com.alibaba.fastjson2.JSON;
import com.bryan.cloud.entities.PayDTO;
import com.bryan.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {
//    private static final String PaymentSrv_URL = "http://127.0.0.1:8001";
    private static final String PaymentSrv_URL = "http://cloud-payment-service"; // 注册中心里的服务名字

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/consumer/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO) {
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/" + id, ResultData.class);
    }

    @PutMapping(value = "/consumer/pay/update")
    public ResultData updateOrder(@RequestBody PayDTO payDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String json = JSON.toJSONString(payDTO);

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<ResultData> exchange = restTemplate.exchange(PaymentSrv_URL + "/pay/update", HttpMethod.PUT, entity, ResultData.class);

        return exchange.getBody();
    }

    @DeleteMapping(value = "/consumer/pay/delete/{id}")
    public ResultData deleteOrder(@PathVariable("id") Integer id) {
        ResponseEntity<ResultData> exchange = restTemplate.exchange(PaymentSrv_URL + "/pay/del/" + id, HttpMethod.DELETE, null, ResultData.class);

        return exchange.getBody();
    }

    @GetMapping(value = "/consumer/pay/get/info")
    public String getInfoByConsul() {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }

    @GetMapping("/consumer/discovery")
    public String discovery() {
        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);

        System.out.println("------------------------------------");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            System.out.printf("%s\t%s\t%s\t%s\n".formatted(instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri()));
        }

        return "%s\t%s".formatted(instances.get(0).getHost(), instances.get(0).getPort());
    }
}
