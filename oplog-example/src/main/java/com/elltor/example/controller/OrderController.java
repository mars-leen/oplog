package com.elltor.example.controller;

import com.elltor.example.entity.Order;
import com.elltor.example.service.IOrderService;
import com.elltor.oplog.annotation.LogRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private IOrderService orderService;

    @ApiOperation("获取订单")
    @GetMapping("/{id}")
    public Object getOrderById(@PathVariable("id") Long id) throws Exception {
        return orderService.getOrderById(id);
    }

    @LogRecord(success = "创建订单成功了，订单号为：{#order.id}", bizNo = "{#order.id}",
            fail = "{#calc(#order.id)}", operator = "{#order.name}", category = "ORDER_LOG")
    @ApiOperation("插入订单")
    @ApiImplicitParam(name = "order", value = "订单", paramType = "body", dataType = "Order")
    @PostMapping
    public Object insertOrder(Order order) {
        long start = System.currentTimeMillis();
        orderService.insert(order);

        System.out.println("-------\n" + (System.currentTimeMillis() - start) + "\n---------");
        return "OK";
    }
}