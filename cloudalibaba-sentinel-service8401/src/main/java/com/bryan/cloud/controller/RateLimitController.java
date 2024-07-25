package com.bryan.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {
    /**
     * 按照REST地址限流
     *
     * @return
     */
    @GetMapping("/ratelimit/byURL")
    public String byURL() {
        return "按REST地址限流---------OK";
    }

    /**
     * 按资源名称限流
     *
     * @return
     */
    @GetMapping("/ratelimit/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handlerBlockHandler")
    public String byResource() {
        return "按资源名称SentinelResource限流-------------OK";
    }

    public String handlerBlockHandler(BlockException exception) {
        return "服务不可用，触发了@SentinelResource启动，请稍候尝试";
    }

    /**
     * 1.   blockHandler用于处理配置sentinel之后出现的违规情况
     * 2.   fallback用于处理JVM抛出异常后服务降级的情况
     * @param p1
     * @return
     */
    @GetMapping("/ratelimit/doAction/{p1}")
    @SentinelResource(value = "doActionSentinelResource",
                        blockHandler = "doActionBlockHandler",
                        fallback = "doActionFallback")
    public String doAction(@PathVariable("p1") Integer p1) {
        if (p1 == 0) {
            throw new RuntimeException("p1等于0");
        }
        return "doAction";
    }

    public String doActionBlockHandler(@PathVariable("p1") Integer p1, BlockException e)  {
        log.error("sentinel配置自定义限流：{}", e);
        return "sentinel配置了自定义限流";
    }

    public String doActionFallback(@PathVariable("p1") Integer p1, Throwable t) {
        log.error("程序逻辑异常：{}", t);
        return "程序逻辑异常" + "\t" + t.getMessage();
    }

    /**
     * 热点参数限流
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotkey")
    @SentinelResource(value = "testHotkey", blockHandler = "testHotkeyBlockHandler")
    public String testHotkey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "--------------testHotkey";
    }

    public String testHotkeyBlockHandler(String p1, String p2, BlockException e) {
        return "-------------testHotkeyBlockHandler";
    }
}
