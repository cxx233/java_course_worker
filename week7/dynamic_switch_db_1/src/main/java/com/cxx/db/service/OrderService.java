package com.cxx.db.service;

import com.cxx.db.entity.Order;
import com.cxx.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单servie
 * @author cxx
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;


    public Order get(String orderId) {
        Order order = orderMapper.getById(orderId);
        return order;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public void insert(Order order) {
        orderMapper.insert(order);
    }

}
