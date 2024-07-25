package com.bryan.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.bryan.cloud.apis.PayFeignApi;
import com.bryan.cloud.entities.PayDTO;
import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping(value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO) {
        return payFeignApi.addPay(payDTO);
    }

    @GetMapping(value = "/feign/pay/get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id) {
        ResultData resultData = null;
        try {
            System.out.println("调用开始-------" + DateUtil.now());
            resultData = payFeignApi.getPayInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-------" +  DateUtil.now());
            resultData = ResultData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return resultData;
    }

    @GetMapping(value = "/feign/pay/get/info")
    public ResultData<String> testBalance() {
        return payFeignApi.getGatewayInfo();
    }
}
