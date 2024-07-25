package com.bryan.cloud.service;

public interface AccountService {
    /**
     * 扣减账号余额
     * @param userId
     * @param money
     */
    void decrease(Long userId, Long money);
}
