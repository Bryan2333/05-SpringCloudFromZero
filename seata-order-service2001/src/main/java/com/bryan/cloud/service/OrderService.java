package com.bryan.cloud.service;

import com.bryan.cloud.entities.Order;

public interface OrderService {
    /**
     * 创建订单
     * @param order
     */
    void create(Order order);
}
