package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HouseHold;
import com.mit.community.mapper.HouseHoldMapper;
import org.omg.PortableInterceptor.HOLDING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 住户业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class HouseHoldService {
    @Autowired
    private HouseHoldMapper houseHoldMapper;

    /**
     * 添加住户信息
     *
     * @param house 住户信息
     * @author Mr.Deng
     * @date 19:34 2018/11/14
     */
    public void save(HouseHold house) {
        houseHoldMapper.insert(house);
    }

    /**
     * 获取所有住户信息
     *
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 19:35 2018/11/14
     */
    public List<HouseHold> list() {
        return houseHoldMapper.selectList(null);
    }

    /**
     * 获取小区男女人数
     *
     * @return 男女人数
     * @author Mr.Deng
     * @date 16:40 2018/11/19
     */
    public Map<String, Object> getSex(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("SUM(CASE gender WHEN '0' THEN 1 else 0 END) boy" +
                ",SUM(CASE gender WHEN '1' THEN 1 else 0 END) girl");
        wrapper.where("community_code={0}", communityCode);
        return houseHoldMapper.selectMaps(wrapper).get(0);
    }

}
