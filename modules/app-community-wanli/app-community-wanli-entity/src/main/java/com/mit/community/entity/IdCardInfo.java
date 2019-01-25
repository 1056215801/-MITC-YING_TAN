package com.mit.community.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * 提取身份证相关信息
 *
 * @author Mr.Deng
 * @date 2018/11/20 16:02
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class IdCardInfo {

    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String region;
    /**
     * 性别.0男1女
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**年龄*/
    private Integer age;

}
