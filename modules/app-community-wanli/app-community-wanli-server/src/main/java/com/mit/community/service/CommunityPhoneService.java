package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.CommunityPhone;
import com.mit.community.mapper.CommunityPhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区电话业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 15:52
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class CommunityPhoneService {
    @Autowired
    private CommunityPhoneMapper communityPhoneMapper;

    /**
     * 查询社区电话，通过小区code和电话类型
     * @param communityCode 小区code
     * @param type          社区电话类型.关联字典code community_phone_type   社区电话类型1、物业电话；2、紧急电话
     * @return 社区电话列表
     * @author Mr.Deng
     * @date 15:55 2018/12/5
     */
    public List<CommunityPhone> listByCommunityCodeAndType(String communityCode, String type) {
        EntityWrapper<CommunityPhone> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("type", type);
        return communityPhoneMapper.selectList(wrapper);
    }
}
