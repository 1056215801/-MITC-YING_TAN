package com.mit.community.config.dynamicDataSouce;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;



/**
 * 动态数据源.
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
	
    @Override
    protected Object determineCurrentLookupKey() {
       /*
        * DynamicDataSourceContextHolder代码中使用setDataSource
        * 设置当前的数据源，在路由类中使用getDataSource进行获取，
        *  交给AbstractRoutingDataSource进行注入使用。
        */
    	log.info("数据源为："+ DynamicDataSourceHolder.getDataSource());
       return DynamicDataSourceHolder.getDataSource();

    }

}