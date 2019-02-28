package com.mit.community.config.dynamicDataSouce;

public class DynamicDataSourceHolder {

    /**
     * 本地线程共享对象
     */
    private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setDataSource(String dataSourceName) {
        THREAD_LOCAL.set(dataSourceName);
    }

    public static String getDataSource() {
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource() {
        THREAD_LOCAL.remove();
    }

//    public static List<String> dataSourceIds = new ArrayList<>();
//
//    /**
//     * 判断指定DataSrouce当前是否存在
//     * @param dataSourceId
//     * @author Mr.Deng
//     * @date 9:06 2019/2/19
//     */
//    public static boolean containsDataSource(String dataSourceId) {
//        return dataSourceIds.contains(dataSourceId);
//    }

}
