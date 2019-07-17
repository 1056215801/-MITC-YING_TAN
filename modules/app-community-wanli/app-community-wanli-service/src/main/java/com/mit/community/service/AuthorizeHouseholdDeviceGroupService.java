package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AuthorizeHouseholdDeviceGroup;
import com.mit.community.mapper.AuthorizeHouseholdDeviceGroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 住户设备授权
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
@Service
public class AuthorizeHouseholdDeviceGroupService extends ServiceImpl<AuthorizeHouseholdDeviceGroupMapper, AuthorizeHouseholdDeviceGroup> {

    private final AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceMapper;

    @Autowired
    public AuthorizeHouseholdDeviceGroupService(AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceMapper) {
        this.authorizeHouseholdDeviceMapper = authorizeHouseholdDeviceMapper;
    }


    /***
     * 保存
     * @param authorizeHouseholdDeviceGroup 住户设备授权
     * @author shuyy
     * @date 2018/11/19 17:11
     * @company mitesofor
    */
    public void save(AuthorizeHouseholdDeviceGroup authorizeHouseholdDeviceGroup){
        authorizeHouseholdDeviceMapper.insert(authorizeHouseholdDeviceGroup);
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/21 10:06
     * @company mitesofor
    */
    public void remove(){
        authorizeHouseholdDeviceMapper.delete(null);
    }

    /**
     * 根据住户id查询授权列表
     * @param householdId
     * @return
     */
    public List<AuthorizeHouseholdDeviceGroup> listByHouseholdId(Integer householdId) {
        EntityWrapper<AuthorizeHouseholdDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        return authorizeHouseholdDeviceMapper.selectList(wrapper);
    }

    /**
     * 根据住户id删除APP授权设备组列表
     */
    public void deleteByHouseholdId(Integer householdId) {
        EntityWrapper<AuthorizeHouseholdDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        authorizeHouseholdDeviceMapper.delete(wrapper);
    }

    @Transactional
    public void addHoldAuthGroup(Integer houseHoidId, String deviceGroupId) {
        if (StringUtils.isNotBlank(deviceGroupId)) {
            EntityWrapper<AuthorizeHouseholdDeviceGroup> wrapper = new EntityWrapper<>();
            wrapper.eq("household_id", houseHoidId);
            authorizeHouseholdDeviceMapper.delete(wrapper);

            String[] deviceGroupIds = deviceGroupId.split(",");
            AuthorizeHouseholdDeviceGroup authorizeHouseholdDeviceGroup = null;
            for(int i=0; i< deviceGroupIds.length; i++) {
                authorizeHouseholdDeviceGroup = new AuthorizeHouseholdDeviceGroup();
                authorizeHouseholdDeviceGroup.setHouseholdId(houseHoidId);
                authorizeHouseholdDeviceGroup.setDeviceGroupId(Integer.valueOf(deviceGroupIds[i]));
                authorizeHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                authorizeHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                authorizeHouseholdDeviceMapper.insert(authorizeHouseholdDeviceGroup);
            }
        }
    }

}
