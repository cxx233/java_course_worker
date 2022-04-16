package com.cxx.insert;

import com.cxx.insert.entity.Commodity;
import com.cxx.insert.entity.Customers;
import com.cxx.insert.entity.Order;
import com.cxx.insert.mapper.CommodityMapper;
import com.cxx.insert.mapper.CustomersMapper;
import com.cxx.insert.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private OrderMapper orderMapper;

    private final int customersNums = 1000;

    private final int commodityNums = 100000;

    @Test
    void mockSingleInsertCustomers() {

        for (int i = 0; i < customersNums; i++) {
            Customers customers = new Customers();
            customers.setCustomerId(String.valueOf(i));
            customers.setCustomerName(String.valueOf(i));
            customers.setLoginNo(String.valueOf(i));
            customersMapper.insertSelective(customers);
        }
    }

    /**
     * 单个插入商品
     */
    @Test
    void mockSingleInsertCommodity() {
        Date now = new Date();
        long allStartTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            long currentStartTimeMillis = System.currentTimeMillis();
            Commodity commodity = new Commodity();
            commodity.setCommodityId(String.valueOf(i));
            commodity.setName(String.format("第%s个商品", i));
            commodity.setPrice((long) i);
            commodity.setCreateTime(now);
            commodityMapper.insertSelective(commodity);
            long currentEndTimeMills = System.currentTimeMillis();
            System.out.printf("commodity 的ID: %s, 插入所需时间 %s ms \r\n", commodity.getCommodityId(), currentEndTimeMills - currentStartTimeMillis);
        }
        long allEndTimeMillis = System.currentTimeMillis();
        System.out.printf("插入 %s 条 所需时间 %s ms \r\n", commodityNums, allEndTimeMillis - allStartTimeMillis);
        // 单条插入
    }

    /**
     * 单个插入订单
     */
    @Test
    void mockSingleOrder() {
        long allStartTimeMillis = System.currentTimeMillis();
        Date now = new Date();
        // 一百个人，一千个商品
        int customers = 100;
        int commoditys = 1000;
        for (int i = 0; i < customers; i++) {
            for (int j = 0; j < commoditys; j++) {
                long currentStartTimeMillis = System.currentTimeMillis();
                String id = String.format("%s_%s", i, j);
                Order order = new Order();
                order.setOrderId(id);
                order.setCustomerId(String.valueOf(i));
                order.setCreateTime(now);
                orderMapper.insertSelective(order);
                long currentEndTimeMills = System.currentTimeMillis();
                System.out.printf("commodity 的ID: %s, 插入所需时间 %s ms \r\n", order.getOrderId(), currentEndTimeMills - currentStartTimeMillis);
            }
        }
        long allEndTimeMillis = System.currentTimeMillis();
        System.out.printf("插入 %s 条 所需时间 %s ms \r\n", commodityNums, allEndTimeMillis - allStartTimeMillis);
        // 插入 100000 条 所需时间 4064427 ms
    }

    /**
     * 批量插入情况
     */
    @Test
    void mockBatchInsertOrder() {
        long allStartTimeMillis = System.currentTimeMillis();
        Date now = new Date();
        // 一百个人，一千个商品
        int startCustomer = 101;
        int customers = 201;

        int startCommoditys = 10001;
        int endCommoditys = 20001;
        int count = 0;
        for (int i = startCustomer; i < customers; i++) {
            List<Order> list = new LinkedList<>();
            long currentStartTimeMillis = System.currentTimeMillis();
            for (int j = startCommoditys; j < endCommoditys; j++) {
                String id = String.format("%s_%s", i, j);
                Order order = new Order();
                order.setOrderId(id);
                order.setCustomerId(String.valueOf(i));
                order.setCreateTime(now);
                list.add(order);
                count ++;
            }
            orderMapper.batchInsert(list);
            long currentEndTimeMills = System.currentTimeMillis();
            System.out.printf("批量插入 %s 条, 插入所需时间 %s ms \r\n", endCommoditys - startCommoditys, currentEndTimeMills - currentStartTimeMillis);
        }
        long allEndTimeMillis = System.currentTimeMillis();
        System.out.printf("总插入 %s 条 所需时间 %s ms \r\n", count, allEndTimeMillis - allStartTimeMillis);

        // result:总插入 1000000 条 所需时间 120549 ms => 120s
    }

}
