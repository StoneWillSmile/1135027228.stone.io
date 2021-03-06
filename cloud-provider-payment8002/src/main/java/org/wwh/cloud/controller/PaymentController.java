package org.wwh.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.wwh.cloud.po.Payment;
import org.wwh.cloud.service.PaymentService;
import org.wwh.cloud.vo.CommonResult;

import javax.annotation.Resource;

/**
 * @author wwh
 * @date 2020/9/14 19:48
 */
@Api(tags = "支付模块controller接口")
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @ApiOperation("创建一条支付信息")
    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        System.out.println(payment);
        int result = paymentService.create(payment);
        log.info("插入{}条数据", result);
        if (result >= 1) {
            return new CommonResult<>(200, "插入成功, serverPort:" + serverPort, payment);
        } else {
            return new CommonResult<>(400, "插入失败", null);
        }
    }

    @ApiOperation("根据id查询")
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询到数据{}", payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功, serverPort:" + serverPort, payment);
        } else {
            return new CommonResult<>(400, "未查询到相关结果", null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
