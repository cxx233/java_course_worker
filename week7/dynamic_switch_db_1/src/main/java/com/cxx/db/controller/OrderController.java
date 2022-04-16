package com.cxx.db.controller;

import com.cxx.db.entity.Order;
import com.cxx.db.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单接口
 *
 * @author cxx
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public String addOrder(@RequestBody Order order) {
        orderService.insert(order);
        return "success";
    }

    @GetMapping("/{id}")
    public Order getByid(@PathVariable("id") String id) {
        return orderService.get(id);
    }

}
