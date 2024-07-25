package com.bryan.cloud.controller;

import cn.hutool.core.util.IdUtil;
import com.bryan.cloud.entities.Pay;
import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@RestController
public class PayGatewayController {
    @Resource
    private PayService payService;

    @GetMapping("/pay/gateway/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping("/pay/gateway/info")
    public ResultData<String> getGatewayInfo() {
        return ResultData.success("Gateway Info: " + IdUtil.simpleUUID());
    }

    @GetMapping("/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headName = headers.nextElement();
            String headValue = request.getHeader(headName);
            System.out.println("请求头名：" + headName + "\t\t" + "请求头值：" + headValue);
            if ("X-request-bryan1".equalsIgnoreCase(headName) || "X-request-bryan2".equalsIgnoreCase(headName)) {
                result.append(headName).append("\t").append(headValue).append(" ");
            }
        }

        System.out.println("===================================================");
        String customerId = request.getParameter("customerId");
        String customerName = request.getParameter("customerName");
        System.out.println("customerId：" + customerId);
        System.out.println("customerName：" + customerName);
        System.out.println("===================================================");

        return ResultData.success("Gateway Filter测试：" + result);
    }
}
