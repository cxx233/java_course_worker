package com.cxx.db.context;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author cxx
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 从DbSourceContext 中获取所需的数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbSourceContext.getDbSource();
    }
}
