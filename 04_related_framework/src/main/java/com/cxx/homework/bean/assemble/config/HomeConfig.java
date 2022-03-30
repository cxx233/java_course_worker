package com.cxx.homework.bean.assemble.config;

import com.cxx.homework.bean.assemble.scan.Bedroom;
import com.cxx.homework.bean.assemble.scan.BookCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用配置文件进行配置
 */
@Configuration
public class HomeConfig {

    @Autowired
    private BookCase bookCase;

    @Bean
    public Bedroom bedroom(BookCase bookCase) {
        return new Bedroom(bookCase);
    }

}
