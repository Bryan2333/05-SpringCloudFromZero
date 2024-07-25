package com.bryan.cloud.service.impl;

import com.bryan.cloud.apis.AccountFeignApi;
import com.bryan.cloud.apis.StorageFeignApi;
import com.bryan.cloud.entities.Order;
import com.bryan.cloud.mapper.OrderMapper;
import com.bryan.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource // 订单微服务通过OpenFeign调用账号微服务
    private AccountFeignApi accountFeignApi;

    @Resource // 订单微服务通过OpenFeign调用库存微服务
    private StorageFeignApi storageFeignApi;

    @Override
    @GlobalTransactional(name = "bryan-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        // xid全局事务id的检查
        String xid = RootContext.getXID();

        // 新建订单
        log.info("---------------开始新建订单，xid: {}\n", xid);

        // 新建订单的默认状态是0
        order.setStatus(0);

        int insertResult = orderMapper.insertSelective(order);

        // 获取刚插入的订单记录
        Order orderFromDB = null;
        if (insertResult > 0) {
            orderFromDB = orderMapper.selectOne(order);
            log.info("-------------新建订单记录：{}\n", orderFromDB);

            // 扣减库存
            log.info("-------------开始调用库存微服务，扣减库存\n");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("-------------库存扣减完成");

            // 扣减账号余额
            log.info("-------------开始调用账号微服务，扣减余额\n");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("-------------账号余额扣减完成");

            log.info("-------------修改订单状态");
            orderFromDB.setStatus(1);
            Example example = new Example(Order.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id", orderFromDB.getId());
            criteria.andEqualTo("status", 0);

            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, example);

            log.info("-------------修改订单状态完成：{}", updateResult);
            log.info("-------------orderFromDB: {}", orderFromDB);
        }

        log.info("---------------结束新建订单，xid: {}", xid);
    }
}
