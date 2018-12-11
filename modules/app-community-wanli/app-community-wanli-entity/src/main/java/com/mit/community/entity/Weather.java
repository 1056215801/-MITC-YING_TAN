package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气
 * @author Mr.Deng
 * @date 2018/12/11 14:19
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("weather")
public class Weather extends BaseEntity {
    /**
     * 华氏度
     */
    private String tempf;
    /**
     * 实时摄氏度温度
     */
    private String temp;
    /**
     * 城市code
     */
    @TableField("city_code")
    private String cityCode;
    /**
     * 城市英文
     */
    private String cityen;
    /**
     * 城市名
     */
    private String city;
    /**
     * 天气
     */
    private String weather;
    /**
     * 今天时间
     */
    private String today;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * PM2.5
     */
    private String pm25;
    /**
     * 更新时间
     */
    private String uptime;
    /**
     * 天气图片
     */
    @TableField("weather_img")
    private String weatherImg;
    /**
     * 气压
     */
    private String stp;
}
