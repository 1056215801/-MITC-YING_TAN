package com.mit.community.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.weatherInfo;
import com.mit.community.util.HttpUtil;
import org.junit.Test;

/**
 * @author Mr.Deng
 * @date 2018/11/13 14:22
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class Weather {

    // @Test
    public void weatherInfo() {
        String url = "https://api.seniverse.com/v3/weather/now.json?" +
                "key=adfeskm2upezsis0&location=nanchang&language=zh-Hans&unit=c";
        System.out.println(HttpUtil.sendGet(url));
    }

    @Test
    public void ss() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://free-api.heweather.net/s6/weather/now?")
                .append("location=")
                .append("南昌")
                .append("&key=HE1901141248371261");
        String url = stringBuilder.toString();
        String s = HttpUtil.sendGet(url);
        JSONObject json = JSON.parseObject(s);
        JSONArray heWeather6 = json.getJSONArray("HeWeather6");
        JSONObject jsonObject = heWeather6.getJSONObject(0);
        String status = jsonObject.getString("status");
        if ("ok".equals(status)) {
            JSONObject now = jsonObject.getJSONObject("now");
            weatherInfo weatherInfo = JSON.toJavaObject(now, weatherInfo.class);
            System.out.println(weatherInfo);
        }
    }

}
