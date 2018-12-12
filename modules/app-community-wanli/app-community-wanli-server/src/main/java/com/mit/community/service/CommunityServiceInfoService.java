package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.CommunityServiceInfo;
import com.mit.community.mapper.CommunityServiceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区门诊业务表
 * @author Mr.Deng
 * @date 2018/12/5 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class CommunityServiceInfoService {
    @Autowired
    private CommunityServiceInfoMapper communityServiceInfoMapper;

    /**
     * 查询社区门诊信息，通过社区code
     * @param communityCode 小区code
     * @param type          社区服务类型.关联字典code community_service_type 社区服务类型 1、社区门诊2、开锁换锁3、送水到家
     * @return 社区门诊信息列表
     * @author Mr.Deng
     * @date 11:34 2018/12/5
     */
    public List<CommunityServiceInfo> listByCommunityCode(String communityCode, String type) {
        EntityWrapper<CommunityServiceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("type", type);
        return communityServiceInfoMapper.selectList(wrapper);
    }
}
