package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * @company mitesofor
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("person_baseinfo")
public class PersonBaseInfo extends BaseEntity {
    /**
     * 省份证号
     */
    @TableField("id_card_num")
    private String idCardNum;
    /**
     * 姓名
     */
    private String name;
    /**
     * 曾用名
     */
    @TableField("former_name")
    private String formerName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    @TableField("native_place")
    private String nativePlace;
    /**
     * 婚姻状况
     */
    private String matrimony;
    /**
     * 政治面貌
     */
    @TableField("politic_countenance")
    private String politicCountenance;
    /**
     * 学历
     */
    private String education;
    /**
     * 宗教信仰
     */
    private String religion;
    /**
     * 职业类别
     */
    @TableField("job_type")
    private String jobType;
    /**
     * 职业
     */
    private String profession;
    /**
     * 联系方式
     */
    private String cellphone;
    /**
     * 户籍地
     */
    @TableField("place_of_domicile")
    private String placeOfDomicile;
    /**
     * 户籍地详细地址
     */
    @TableField("place_of_domicile_detail")
    private String placeOfDomicileDetail;
    /**
     * 现住地
     */
    @TableField("place_of_reside")
    private String placeOfReside;
    /**
     * 现住地详细地址
     */
    @TableField("place_of_reside_detail")
    private String placeOfResideDetail;
    /**
     * 服务处所
     */
    @TableField("place_of_server")
    private String placeOfServer;
    /**
     * 证件照base64
     */
    @TableField("phot_base64")
    private String photoBase64;
    /**
     * 人口属性(1户籍人口，2流动人口)
     */
    private int rksx;
    /**
     * 出生年
     */
    //private int year;
    /**
     * 出生月
     */
    //private int month;
    /**
     * 出生日
     */
    //private int day;
    /**
     * 年龄
     */
    @TableField(exist = false)
    private int age;
}
