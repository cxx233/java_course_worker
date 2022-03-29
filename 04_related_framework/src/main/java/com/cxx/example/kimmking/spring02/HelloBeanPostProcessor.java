package com.cxx.example.kimmking.spring02;

import com.cxx.example.kimmking.spring01.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 前置的初始化方法
 * 和后置的初始化方法内容
 * @Componet 加载到spring 的容器重
 */
@Component
public class HelloBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" ====> postProcessBeforeInitialization " + beanName  +":"+ bean);
        // 可以加点额外处理
        // 例如
        if (bean instanceof Student) {
            Student student = (Student) bean;
            student.setName(student.getName() + "-" + System.currentTimeMillis());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(" ====> postProcessAfterInitialization " + beanName +":"+ bean);
        return bean;
    }
}
