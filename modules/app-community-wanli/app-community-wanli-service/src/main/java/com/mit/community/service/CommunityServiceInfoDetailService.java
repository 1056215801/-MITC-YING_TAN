package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.CommunityServiceInfoDetail;
import com.mit.community.mapper.CommunityServiceInfoDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区服务详情
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@Service
public class CommunityServiceInfoDetailService {
    @Autowired
    private CommunityServiceInfoDetailMapper communityServiceInfoDetailMapper;

    /**
     * 保存
     *
     * @param communityServiceInfoDetail 社区服务详情
     * @author shuyy
     * @date 2018/12/20 16:56
     * @company mitesofor
     */
    public void save(CommunityServiceInfoDetail communityServiceInfoDetail) {
        communityServiceInfoDetailMapper.insert(communityServiceInfoDetail);
    }

    /**
     * 查询，通过社区服务id
     *
     * @param communityServiceInfoId 社区服务id
     * @return com.mit.community.entity.CommunityServiceInfoDetail
     * @author shuyy
     * @date 2018/12/20 17:15
     * @company mitesofor
     */
    public CommunityServiceInfoDetail getByCommunityServiceInfoId(Integer communityServiceInfoId) {
        EntityWrapper<CommunityServiceInfoDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("community_service_info_id", communityServiceInfoId);
        List<CommunityServiceInfoDetail> communityServiceInfoDetails = communityServiceInfoDetailMapper.selectList(wrapper);
        if (communityServiceInfoDetails.isEmpty()) {
            return null;
        }
        return communityServiceInfoDetails.get(0);
    }

    /**
     * 删除,通过社区服务id
     * @param communityServiceInfoId 社区服务id
     * @author shuyy
     * @date 2018/12/21 15:27
     * @company mitesofor
     */
    public void removeByCommunityServiceId(Integer communityServiceInfoId) {
        EntityWrapper<CommunityServiceInfoDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("community_service_info_id", communityServiceInfoId);
        communityServiceInfoDetailMapper.delete(wrapper);
    }

    /**
     * 更新
     *
     * @param communityServiceInfoDetail 社区服务详情
     * @author shuyy
     * @date 2018/12/20 17:16
     * @company mitesofor
     */
    public void update(CommunityServiceInfoDetail communityServiceInfoDetail) {
        communityServiceInfoDetailMapper.updateById(communityServiceInfoDetail);
    }
}
