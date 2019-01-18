package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气
 * @author Mr.Deng
 * @date 2019/1/14 15:24
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    /**
     * 相对湿度
     */
    private Integer hum;
    /**
     * 能见度，默认单位：公里
     */
    private Integer vis;
    /**
     * 大气压强
     */
    private Integer pres;
    /**
     * 降雨量
     */
    private Double pcpn;
    /**
     * 体感温度，默认单位：摄氏度
     */
    private Integer fl;
    /**
     * 风力
     */
    private Integer wind_sc;
    /**
     * 风向
     */
    private String wind_dir;
    /**
     * 风速，公里/小时
     */
    private Integer wind_spd;
    /**
     * 云量
     */
    private Integer cloud;
    /**
     * 风向360角度
     */
    private Integer wind_deg;
    /**
     * 温度，默认单位：摄氏度
     */
    private Integer tmp;
    /**
     * 实况天气状况描述
     */
    private String cond_txt;
    /**
     * 实况天气状况代码
     */
    private Integer cond_code;

}
