package com.mit.community.service;

import com.mit.community.entity.Zone;
import com.mit.community.mapper.ZoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分区业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 15:53
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ZoneService {

    private final ZoneMapper zoneMapper;

    @Autowired
    public ZoneService(ZoneMapper zoneMapper) {
        this.zoneMapper = zoneMapper;
    }

    /**
     * 保存分区信息
     *
     * @param zone 分区信息
     * @author Mr.Deng
     * @date 15:55 2018/11/14
     */
    public void save(Zone zone) {
        zoneMapper.insert(zone);
    }

    /**
     * 获取所有的小区信息
     *
     * @return 小区列表
     * @author Mr.Deng
     * @date 16:16 2018/11/14
     */
    public List<Zone> list() {
        return zoneMapper.selectList(null);
    }
}
