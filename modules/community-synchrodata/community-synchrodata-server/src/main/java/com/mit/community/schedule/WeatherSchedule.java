package com.mit.community.schedule;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.Region;
import com.mit.community.entity.Weather;
import com.mit.community.service.RegionService;
import com.mit.community.service.WeatherService;
import com.mit.community.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 天气定时同步
 * @author Mr.Deng
 * @date 2018/12/11 14:41
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Component
public class WeatherSchedule {

    private final WeatherService weatherService;
    private final RegionService regionService;

    @Autowired
    public WeatherSchedule(RegionService regionService, WeatherService weatherService) {
        this.regionService = regionService;
        this.weatherService = weatherService;
    }

    /**
     * 同步获取天气信息
     * @author Mr.Deng
     * @date 15:01 2018/12/11
     */
    @Scheduled(cron = "0 */30 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport() {
        String[] regions = {"nanchang", "yingtan"};
        List<Region> regionList = regionService.getByEnglishName(regions);
        weatherService.remove();
        if (!regionList.isEmpty()) {
            for (Region region : regionList) {
                String url = "http://api.help.bj.cn/apis/weather/?id=" + region.getCityCode();
                String result = HttpUtil.sendGet(url);
                JSONObject json = JSONObject.parseObject(result);
                Weather weather = new Weather();
                weather.setCity(json.getString("city"));
                weather.setCityCode(json.getString("citycode"));
                weather.setCityen(json.getString("cityen"));
                weather.setHumidity(json.getString("humidity"));
                weather.setPm25(json.getString("pm25"));
                weather.setStp(json.getString("stp"));
                weather.setTemp(json.getString("temp"));
                weather.setTempf(json.getString("tempf"));
                weather.setToday(json.getString("today"));
                weather.setUptime(json.getString("uptime"));
                weather.setWeather(json.getString("weather"));
                weather.setWeatherImg(json.getString("weatherimg"));
                weatherService.save(weather);
            }
        }

    }

}
