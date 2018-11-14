package com.mit.community.service;

import com.mit.community.entity.House;
import com.mit.community.mapper.HouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 住户业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class HouseService {
    @Autowired
    private HouseMapper houseMapper;

    /**
     * 添加住户信息
     *
     * @param house 住户信息
     * @author Mr.Deng
     * @date 19:34 2018/11/14
     */
    public void save(House house) {
        houseMapper.insert(house);
    }

    /**
     * 获取所有住户信息
     *
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 19:35 2018/11/14
     */
    public List<House> getHouseList() {
        return houseMapper.selectList(null);
    }

}
