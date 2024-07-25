package com.bryan.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bryan.cloud.entities.PayDTO;
import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.resp.ReturnCodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PayAlibabaController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/pay/nacos/{id}")
    public String getPayInfo(@PathVariable("id") Integer id) {
        return "Nacos registry, server port: " + serverPort + ", id: " + id;
    }

    // OpenFeign + Sentinel进行服务降级和流量监控
    @GetMapping(value = "/pay/nacos/get/{orderNo}")
    @SentinelResource(value = "getPayByOrderNO", blockHandler = "handlerBlockHandler")
    public ResultData getPayByOrderNo(@PathVariable("orderNo") String orderNo) {
        PayDTO payDTO = new PayDTO();
        payDTO.setId(1024);
        payDTO.setOrderNo(orderNo);
        payDTO.setAmount(BigDecimal.valueOf(9.9));
        payDTO.setPayNo(IdUtil.fastSimpleUUID());

        return ResultData.success(payDTO);
    }

    public ResultData handlerBlockHandler(@PathVariable("orderNo") String orderNo, BlockException e) {
        return ResultData.error(ReturnCodeEnum.RC500.getCode(),
                "getPayByOrderNo服务不可用，触发sentinel流控规则，请稍候尝试");
    }
}
