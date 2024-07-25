package com.bryan.cloud.controller;

import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @GetMapping("/account/decrease")
    public ResultData decrease(@RequestParam("userId") Long userId,
                               @RequestParam("money") Long money) {
        accountService.decrease(userId, money);
        return ResultData.success("扣减用于余额完成");
    }
}
