package com.cxx.db.aop;

import com.cxx.db.context.DbSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AOP 动态切换数据源
 * @author cxx
 */
@Aspect
@Order(-1) // 切片先于@Transactional 执行
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Pointcut("execution( * com.cxx.db.mapper.*.*(..))")
    public void daoAspect() {
    }

    /**
     * 切换数据源
     * @param point
     * @param dbSource
     */
    //注解在类对象，拦截有@DbSource类下所有的方法
    @Before("daoAspect()&&@annotation(dbSource)")
    public void switchDataSource(JoinPoint point, DbSource dbSource) {
//    public void switchDataSource(JoinPoint point) {
        log.error("进入到switchDataSource里面了");
        if (dbSource != null) {
            // 切换数据源
            DbSourceContext.setDbSource(dbSource.value());
        }
    }

    /**
     * 重置数据源
     * @param point
     * @param dbSource
     */
    //注解在类对象，拦截有@DbSource类下所有的方法
//    @After("@within(dbSource)")
    @After("daoAspect()")
//    public void restoreDataSource(JoinPoint point, DbSource dbSource) {
    public void restoreDataSource(JoinPoint point) {
        // 将数据源重置为默认数据源
        DbSourceContext.clearDbsource();
    }

}
