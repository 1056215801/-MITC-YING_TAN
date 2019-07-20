package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.PersonBaseInfo;
import com.mit.community.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * @company mitesofor
 */
@Service
public class PersonBaseInfoService extends ServiceImpl<PersonBaseInfoMapper, PersonBaseInfo> {
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;


    @Transactional
    public void removeByPhoneList(List<String> phone) {
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.in("cellphone", phone);
        personBaseInfoMapper.delete(wrapper);
    }

    @Transactional
    public void insert(List<HouseHold> list) {
        List<PersonBaseInfo> personList = new ArrayList<>();
        PersonBaseInfo personBaseInfo = null;
        for (int i=0; i < list.size() ; i++) {
            personBaseInfo = new PersonBaseInfo();
            personBaseInfo.setCommunity_code(list.get(i).getCommunityCode());
            personBaseInfo.setCellphone(list.get(i).getMobile());
            personBaseInfo.setName(list.get(i).getHouseholdName());
            if (list.get(i).getGender()==0) {
                personBaseInfo.setGender("男");
            } else {
                personBaseInfo.setGender("女");
            }
            if (StringUtils.isNotBlank(list.get(i).getCredentialNum())){
                personBaseInfo.setIdCardNum(list.get(i).getCredentialNum());
            }
            personBaseInfo.setGmtCreate(LocalDateTime.now());
            personBaseInfo.setGmtModified(LocalDateTime.now());
            personList.add(personBaseInfo);
            if (!StringUtils.isNotBlank(list.get(i).getMobile())) {
                insert(personBaseInfo);
            }
        }
        //insertBatch(personList);
       // System.out.println("===插入成功");
    }

    public Integer getIdByMobile(String phone){
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", phone);
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()){
            return list.get(0).getId();
        } else {
            return null;
        }
    }



}
