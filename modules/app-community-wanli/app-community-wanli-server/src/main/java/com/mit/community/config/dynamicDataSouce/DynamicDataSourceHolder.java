package com.mit.community.config.dynamicDataSouce;

public class DynamicDataSourceHolder {
	
	/**
     * 本地线程共享对象
     */
    private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setDataSource(String dataSourceName){
        THREAD_LOCAL.set(dataSourceName);
    }

    public static String getDataSource(){
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource(){
        THREAD_LOCAL.remove();
    }

}
