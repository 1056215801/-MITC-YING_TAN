package com.mit.auth.common.util.jwt;

/**
 * jwt实体类
* @author: shuyy
* @date: 2018/11/6 16:02
* @company mitesofor
*/
public interface IJWTInfo {
    /**
    * 获取用户名
    * @param 
    * @return: 
    * @author: shuyy
    * @date: 2018/11/6 16:03
    * @company mitesofor
    */
    String getUniqueName();

    /**
    * 获取用户ID
    * @param 
    * @return: 
    * @author: shuyy
    * @date: 2018/11/6 16:03
    * @company mitesofor
    */
    String getId();

    /**
    * 获取名称
    * @param 
    * @return: 
    * @author: shuyy
    * @date: 2018/11/6 16:03
    * @company mitesofor
    */
    String getName();
}
