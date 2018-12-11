package com.mit.community.service;

import com.mit.community.entity.Weather;
import com.mit.community.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 天气业务处理类
 * @author Mr.Deng
 * @date 2018/12/11 14:29
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class WeatherService {

    @Autowired
    private WeatherMapper weatherMapper;

    /**
     * 添加天气
     * @param weather 天气
     * @author Mr.Deng
     * @date 14:31 2018/12/11
     */
    public void save(Weather weather) {
        weather.setGmtCreate(LocalDateTime.now());
        weather.setGmtModified(LocalDateTime.now());
        weatherMapper.insert(weather);
    }

    /**
     * 删除天气信息
     * @author Mr.Deng
     * @date 15:23 2018/12/11
     */
    public void remove() {
        weatherMapper.delete(null);
    }

}
