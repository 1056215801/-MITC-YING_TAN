package com.mit.community.module.system.service;

import com.mit.community.entity.UserLabel;
import com.mit.community.module.system.mapper.UserLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户标签
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserLabelService {

    private final UserLabelMapper userLabelMapper;

    @Autowired
    public UserLabelService(UserLabelMapper userLabelMapper) {
        this.userLabelMapper = userLabelMapper;
    }

    /**
     * 保存
     * @param userLabel userlabel
     * @date 2018/11/29 11:45
     * @company mitesofor
    */
    public void save(UserLabel userLabel){
        userLabel.setGmtCreate(LocalDateTime.now());
        userLabel.setGmtModified(LocalDateTime.now());
        userLabelMapper.insert(userLabel);
    }

}
