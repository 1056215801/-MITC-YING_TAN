package com.dnake.constant;

/**
 * 共用常量
 *
 * @author shuyy
 * @date 2018/11/12
 * @company mitesofor
 */
public class DnakeConstants {

    /**
     * api域名。默认测试服务域名
     */
    public static String serviceUrl = "http://112.74.80.35:8080/cmp";
    /**
     * api正式域名
     */
    private static final String URL_PRODUCT = "http://cmp.ishanghome.com/cmp";
    /**
     * api测试域名
     */
    private static final String URL_TEST = "http://112.74.80.35:8080/cmp";
    /**
     * 环境标示。测试环境
     */
    public static final String MODEL_TEST = "test";
    /**
     * 环境标示。生成环境
     */
    public static final String MODEL_PRODUCT = "product";

    /**集群accountid，i尚对讲：pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs;安智家园：gwBvDnK3Z4YqlU8IxKUrOrK2Z66zgELo；*/
    public static final String CLUSTER_ACCOUNT_ID = "pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs";

    /**
     * @param model 访问地址类型 正式/测试
     * @author shuyy
     * @date 8:51 2018/11/12
     */
    public static void choose(String model) {
        if (model.equals(MODEL_PRODUCT)) {
            serviceUrl = URL_PRODUCT;
        } else if (model.equals(MODEL_TEST)) {
            serviceUrl = URL_TEST;
        }
    }
}
