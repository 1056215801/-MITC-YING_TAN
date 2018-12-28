package com.mit.community.schedule;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mit.common.util.HttpClientUtil;
import com.mit.community.entity.Region;
import com.mit.community.entity.Weather;
import com.mit.community.service.RegionService;
import com.mit.community.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 天气定时同步
 *
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
     *
     * @author Mr.Deng
     * @date 15:01 2018/12/11
     */
    @Scheduled(cron = "0 0 */6 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport() {
        List<Region> regionList = regionService.list();
        List<Weather> weathers = Lists.newArrayListWithCapacity(regionList.size());
        if (!regionList.isEmpty()) {
            int tmp = 200;
            for (int i = 0; i < regionList.size(); i++) {
                if (i > tmp) {
                    tmp += 200;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(i);
                Region region = regionList.get(i);
                String url = "http://api.help.bj.cn/apis/weather/?id=" + region.getCityCode();
                String result = HttpClientUtil.getMethodRequestResponse(url);
//                 HttpUtil.sendGet(url);
                if (result == null) {
                    continue;
                }
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
                weathers.add(weather);
            }
        }
        if (!weathers.isEmpty()) {
            weatherService.remove();
            weatherService.insertBatch(weathers);
        }

    }

}
