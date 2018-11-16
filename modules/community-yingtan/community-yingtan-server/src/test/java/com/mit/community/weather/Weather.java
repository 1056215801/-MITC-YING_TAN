package com.mit.community.weather;

import com.mit.community.util.HttpUtil;
import org.junit.Test;

/**
 * @author Mr.Deng
 * @date 2018/11/13 14:22
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class Weather {

    @Test
    public void weatherInfo() {
        String url = "https://api.seniverse.com/v3/weather/now.json?" +
                "key=adfeskm2upezsis0&location=nanchang&language=zh-Hans&unit=c";
        System.out.println(HttpUtil.sendGet(url));
    }

}
