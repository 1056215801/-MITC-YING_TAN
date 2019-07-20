package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.mit.community.entity.WlPersonBaseInfo;

import com.mit.community.module.pass.mapper.WlPersonBaseInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * @company mitesofor
 */
@Service
public class WlPersonBaseInfoService {
    @Autowired
    private WlPersonBaseInfoMapper personBaseInfoMapper;


    public Integer getIdByCardNum(String idCardNum) {
        Integer id = null;
        EntityWrapper<WlPersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id_card_num", idCardNum);
        List<WlPersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
        }
        return id;
    }

}
