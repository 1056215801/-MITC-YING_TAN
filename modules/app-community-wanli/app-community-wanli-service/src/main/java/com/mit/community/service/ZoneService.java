package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Zone;
import com.mit.community.mapper.ZoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分区业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 17:50
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ZoneService {
    @Autowired
    private ZoneMapper zoneMapper;

    /**
     * 查询分区信息，通过小区code
     * @param communityCode 小区code
     * @return 分区列表
     * @author Mr.Deng
     * @date 17:57 2018/12/7
     */
    public List<Zone> listByCommunityCode(String communityCode) {
        EntityWrapper<Zone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("zone_status", 1);
        return zoneMapper.selectList(wrapper);
    }
}
