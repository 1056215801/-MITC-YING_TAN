package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Weather;
import com.mit.community.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 天气业务处理层
 * @author Mr.Deng
 * @date 2018/12/11 15:35
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class WeatherService {
    @Autowired
    private WeatherMapper weatherMapper;

    /**
     * 查询天气信息，通过城市英文
     * @param cityEnglish 城市英文
     * @return 天气信息
     * @author Mr.Deng
     * @date 15:41 2018/12/11
     */
    public Weather ByCityeEnglish(String cityEnglish) {
        EntityWrapper<Weather> wrapper = new EntityWrapper<>();
        wrapper.eq("cityen", cityEnglish);
        return weatherMapper.selectList(wrapper).get(0);
    }
}
