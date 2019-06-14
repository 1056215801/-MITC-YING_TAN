package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Weather;
import com.mit.community.entity.WeatherInfo;
import com.mit.community.entity.WeatherVo;
import com.mit.community.mapper.WeatherMapper;
import com.mit.community.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 天气业务处理层
 *
 * @author Mr.Deng
 * @date 2018/12/11 15:35
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class WeatherService {
    @Autowired
    private WeatherMapper weatherMapper;


    @Cache(key = "weather{1}", expire = 60)
    public WeatherInfo getWeather(String cityName) {
        if (StringUtils.isNotBlank(cityName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://free-api.heweather.net/s6/weather/now?")
                    .append("location=")
                    .append(cityName)
                    .append("&key=db098c07c33b44d08c8cd2b9b6298a34");
            String url = stringBuilder.toString();
            String s = HttpUtil.sendGet(url);
            JSONObject json = JSON.parseObject(s);
            JSONArray heWeather6 = json.getJSONArray("HeWeather6");
            JSONObject jsonObject = heWeather6.getJSONObject(0);
            String status = jsonObject.getString("status");
            if ("ok".equals(status)) {
                JSONObject now = jsonObject.getJSONObject("now");
                WeatherInfo weatherInfo = JSON.toJavaObject(now, WeatherInfo.class);
                return weatherInfo;
            }
            return null;
        }
        return null;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/4/30 11:34
     * @Company mitesofor
     * @Description:~天气查询接口
     */
    @Cache(key = "weather{1}", expire = 60)
    public WeatherVo getWeatherInfo(String cityName) {
        WeatherVo weatherVo = new WeatherVo();
        if (StringUtils.isNotBlank(cityName)) {
            StringBuilder sb = new StringBuilder();
            sb.append("https://www.tianqiapi.com/api/?version=v1&city=").append(cityName);
            String url = sb.toString();
            String res = HttpUtil.sendGet(url);
            JSONObject json = JSON.parseObject(res);
            JSONArray data = json.getJSONArray("data");
            JSONObject jsonObject = data.getJSONObject(0);
            weatherVo = JSON.toJavaObject(jsonObject, WeatherVo.class);
        }
        return weatherVo;
    }

    /**
     * 查询天气信息，通过城市英文
     *
     * @param cityEnglish 城市英文
     * @return 天气信息
     * @author Mr.Deng
     * @date 15:41 2018/12/11
     */
    public Weather ByCityeEnglish(String cityEnglish) {
        EntityWrapper<Weather> wrapper = new EntityWrapper<>();
        wrapper.eq("cityen", cityEnglish);
        List<Weather> weathers = weatherMapper.selectList(wrapper);
        if (weathers.isEmpty()) {
            return null;
        }
        return weathers.get(0);
    }
}
