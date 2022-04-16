package com.cxx.insert.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_orders.order_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private String orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_orders.customer_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private String customerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_orders.custmer_shipping_address_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private String custmerShippingAddressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_orders.create_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_orders.update_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_orders
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_orders.order_id
     *
     * @return the value of t_orders.order_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_orders.order_id
     *
     * @param orderId the value for t_orders.order_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_orders.customer_id
     *
     * @return the value of t_orders.customer_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_orders.customer_id
     *
     * @param customerId the value for t_orders.customer_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_orders.custmer_shipping_address_id
     *
     * @return the value of t_orders.custmer_shipping_address_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public String getCustmerShippingAddressId() {
        return custmerShippingAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_orders.custmer_shipping_address_id
     *
     * @param custmerShippingAddressId the value for t_orders.custmer_shipping_address_id
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public void setCustmerShippingAddressId(String custmerShippingAddressId) {
        this.custmerShippingAddressId = custmerShippingAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_orders.create_time
     *
     * @return the value of t_orders.create_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_orders.create_time
     *
     * @param createTime the value for t_orders.create_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_orders.update_time
     *
     * @return the value of t_orders.update_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_orders.update_time
     *
     * @param updateTime the value for t_orders.update_time
     *
     * @mbg.generated Fri Apr 15 12:48:50 CST 2022
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}