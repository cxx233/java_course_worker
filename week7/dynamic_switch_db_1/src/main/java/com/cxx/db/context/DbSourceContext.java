package com.cxx.db.context;


import lombok.extern.slf4j.Slf4j;

/**
 * db 数据源上下文
 */
@Slf4j
public class DbSourceContext {
    // 通过 ThreadLocal 传递当前所需的数据源
    private static final ThreadLocal<String> dbContext = new ThreadLocal<String>();

    public static void setDbSource(String source) {
        log.error("set source ==>" + source);
        dbContext.set(source);
    }

    public static String getDbSource() {
        log.error("get source ==>" + dbContext.get());
        return dbContext.get();
    }

    public static void clearDbsource() {
        dbContext.remove();
    }

}
