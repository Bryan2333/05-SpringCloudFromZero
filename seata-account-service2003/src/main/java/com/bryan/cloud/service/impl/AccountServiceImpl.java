package com.bryan.cloud.service.impl;

import com.bryan.cloud.mapper.AccountMapper;
import com.bryan.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public void decrease(Long userId, Long money) {
        log.info("-------------扣减用户余额开始");
        accountMapper.decrease(userId, money);
//        timeout();
        log.info("-------------扣减用户余额结束");
    }

    private static void timeout() {
        try {
            TimeUnit.SECONDS.sleep(65);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
