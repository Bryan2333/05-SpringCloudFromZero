package com.bryan.cloud.controller;

import com.bryan.cloud.entities.Pay;
import com.bryan.cloud.entities.PayDTO;
import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name = "支付微服务模块", description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/pay/add")
    @Operation(summary = "新增", description = "新增流水的方法，json作为参数")
    public ResultData<String> addPay(@RequestBody Pay pay) {
        log.info(pay.toString());
        int add = payService.add(pay);
        return ResultData.success("成功插入插入记录，返回值：" + add);
    }

    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "删除", description = "删除流水的方法")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id) {
        return ResultData.success(payService.delete(id));
    }

    @PutMapping("/pay/update")
    @Operation(summary = "更新", description = "更新流水的方法")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);

        int update = payService.update(pay);

        return ResultData.success("成功修改记录，返回值：" + update);
    }

    @GetMapping("/pay/get/{id}")
    @Operation(summary = "查询", description = "按ID查询流水")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        if (id == -4) {
            throw new RuntimeException("-4");
        }

        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return ResultData.success(payService.getById(id));
    }

    @GetMapping("/pay/getAll")
    public ResultData<List<Pay>> getAll() {
        return ResultData.success(payService.getAll());
    }

    @GetMapping("/pay/get/info")
    public String getInfoByConsul(@Value("${bryan.info}") String bryanInfo) {
        return "%s %s".formatted(bryanInfo, port);
    }
}
