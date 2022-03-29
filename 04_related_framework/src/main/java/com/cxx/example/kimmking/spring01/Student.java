package com.cxx.example.kimmking.spring01;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;


/**
 * BeanNameAware, ApplicationContextAware
 * 这两个东西 的实现类，被放在spring 容器前，整个初始化过程中，被spring 看到这两个接口，
 * 会调用     void setBeanName(String var1); 和void setApplicationContext(ApplicationContext var1) throws BeansException; 方法，
 * 把当前真实的beanName set 给beanName 属性，以及applicationContext set 给 applicationContext 属性
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable, BeanNameAware, ApplicationContextAware {


    private int id;
    private String name;

    private String beanName;
    private ApplicationContext applicationContext;

    public void init(){
        System.out.println("hello...........");
    }
    
    public static Student create(){
        return new Student(102,"KK102",null, null);
    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("   context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));

    }


}
