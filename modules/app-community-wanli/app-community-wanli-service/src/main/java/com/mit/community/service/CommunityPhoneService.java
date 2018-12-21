package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.CommunityPhone;
import com.mit.community.mapper.CommunityPhoneMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区电话业务处理层
 *
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
     *
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

    /**
     * 保存社区电话
     *
     * @param communityCode 社区code
     * @param name          名
     * @param phone         电话
     * @param type          类型
     * @param creatorUserId 创建人
     * @author shuyy
     * @date 2018/12/21 20:11
     * @company mitesofor
     */
    public void save(String communityCode, String name, String phone, String type, Integer creatorUserId) {
        CommunityPhone communityPhone = new CommunityPhone(communityCode,
                name, phone,
                type, creatorUserId);
        communityPhoneMapper.insert(communityPhone);
    }

    /**
     * 更新
     *
     * @param id            id
     * @param name          名
     * @param phone         电话
     * @param creatorUserId 创建人
     * @author shuyy
     * @date 2018/12/21 20:13
     * @company mitesofor
     */
    public void update(Integer id,
                       String name, String phone, Integer creatorUserId) {
        CommunityPhone communityPhone = new CommunityPhone();
        communityPhone.setId(id);
        if (StringUtils.isNotBlank(name)) {
            communityPhone.setName(name);
        }
        if (StringUtils.isNotBlank(phone)) {
            communityPhone.setPhone(phone);
        }
        communityPhone.setCreatorUserId(creatorUserId);
        communityPhoneMapper.updateById(communityPhone);
    }

    /**
     * 删除
     * @param id id
     * @author shuyy
     * @date 2018/12/21 20:21
     * @company mitesofor
     */
    public void remove(Integer id) {
        communityPhoneMapper.deleteById(id);
    }

    /**
     * 列表
     *
     * @return java.util.List<com.mit.community.entity.CommunityPhone>
     * @author shuyy
     * @date 2018/12/21 20:14
     * @company mitesofor
     */
    public List<CommunityPhone> list() {
        return communityPhoneMapper.selectList(null);
    }


}
