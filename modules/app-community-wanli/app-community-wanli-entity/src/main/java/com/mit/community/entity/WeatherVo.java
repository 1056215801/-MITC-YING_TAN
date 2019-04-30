package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/4/30 13:59
 * @Company mitesofor
 * @Description:天气接口参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherVo {

    private String air;

    private String wea;

    private String tem;
}
