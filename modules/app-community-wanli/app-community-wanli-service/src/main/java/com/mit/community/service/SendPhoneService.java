package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.SendPhoneInfo;
import com.mit.community.mapper.SendPhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HuShanLin
 * @Date Created in 9:36 2019/6/25
 * @Company: mitesofor </p>
 * @Description:~
 */
@Service
public class SendPhoneService {
    @Autowired
    private SendPhoneMapper sendPhoneMapper;

    public List<SendPhoneInfo> getPhoneByDj(int dj){
        EntityWrapper<SendPhoneInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("dj", dj);
        List<SendPhoneInfo> list = sendPhoneMapper.selectList(wrapper);
        return list;
    }
}
