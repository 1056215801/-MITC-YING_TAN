package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.CommunityPhone;
import com.mit.community.mapper.CommunityPhoneMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void save(String communityCode, Integer id, String name, String phone,
                     String type, Integer creatorUserId, Integer displayOrder) {
        if (id == null) {//新增
            CommunityPhone communityPhone = new CommunityPhone(communityCode,
                    name, phone,
                    type, displayOrder, 1, creatorUserId);
            communityPhone.setGmtCreate(LocalDateTime.now());
            communityPhone.setGmtModified(LocalDateTime.now());
            communityPhoneMapper.insert(communityPhone);
        } else {//修改
            EntityWrapper<CommunityPhone> wrapper = new EntityWrapper<>();
            wrapper.eq("id", id);
            List<CommunityPhone> list = communityPhoneMapper.selectList(wrapper);
            CommunityPhone communityPhone = list.get(0);
            communityPhone.setName(name);
            communityPhone.setPhone(phone);
            communityPhone.setType(type);
            communityPhone.setCreatorUserId(creatorUserId);
            communityPhone.setDisplayOrder(displayOrder);
            communityPhone.setGmtModified(LocalDateTime.now());
            communityPhoneMapper.updateById(communityPhone);
        }
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
        communityPhone.setGmtModified(LocalDateTime.now());
        communityPhoneMapper.updateById(communityPhone);
    }

    /**
     * 删除
     *
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
    public Page<CommunityPhone> listByCommunityCode(String communityCode,
                                                    String search_phoneName,
                                                    Integer search_phoneType,
                                                    Integer search_dataStatus,
                                                    Integer search_displayOrder,
                                                    Integer pageNum, Integer pageSize) {
        Page<CommunityPhone> page = new Page<>(pageNum, pageSize);
        EntityWrapper<CommunityPhone> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(search_phoneName)) {
            wrapper.eq("phone", search_phoneName);
        }
        if (search_phoneType != null) {
            wrapper.eq("type", search_phoneType);
        }
        if (search_dataStatus != null) {
            wrapper.eq("status", search_dataStatus);
        }
        if (search_displayOrder != null) {
            wrapper.eq("display_order", search_displayOrder);
        }
        List<CommunityPhone> list = communityPhoneMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public void enable(Integer id) {
        EntityWrapper<CommunityPhone> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<CommunityPhone> list = communityPhoneMapper.selectList(wrapper);
        CommunityPhone phone = list.get(0);
        phone.setStatus(1);
        communityPhoneMapper.updateById(phone);
    }

    public void stop(Integer id) {
        EntityWrapper<CommunityPhone> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<CommunityPhone> list = communityPhoneMapper.selectList(wrapper);
        CommunityPhone phone = list.get(0);
        phone.setStatus(0);
        communityPhoneMapper.updateById(phone);
    }
}
