package com.bryan.cloud.apis;

import com.bryan.cloud.resp.ResultData;
import com.bryan.cloud.resp.ReturnCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class PayFeignSentinelApiFallback implements PayFeignSentinelApi{
    @Override
    public ResultData getPayByOrderNo(String orderNo) {
        return ResultData.error(ReturnCodeEnum.RC500.getCode(), "对方服务暂时不可用，请稍候尝试");
    }
}
