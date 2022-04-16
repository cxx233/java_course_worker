package com.cxx.db.config;

import com.cxx.db.context.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置文件
 * @author cxx
 */
@Configuration
@MapperScan("com.cxx.db.mapper")
public class DynamicDataSourceConfig {
    @Value("mybatis.type-aliases-package")
    private String typeAliasesPackage;

    @Bean(name = "adminDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    public DataSource adminDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //配置多数据源
        Map<Object, Object> dbMap = new HashMap<>();
        dbMap.put("slave2DataSource", slave2DataSource());
        dbMap.put("slave1DataSource", slave1DataSource());
        dbMap.put("adminDataSource", adminDataSource());
        dynamicDataSource.setTargetDataSources(dbMap);

        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(adminDataSource());

        return dynamicDataSource;
    }


    /*
     * 数据库连接会话工厂
     * 将动态数据源赋给工厂
     * mapper存于resources/mapper目录下
     * 默认bean存于com.main.example.bean包或子包下，也可直接在mapper中指定
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource());
//        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-configuration.xml"));
        sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage); //扫描bean
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));    // 扫描映射文件
        return sqlSessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}
