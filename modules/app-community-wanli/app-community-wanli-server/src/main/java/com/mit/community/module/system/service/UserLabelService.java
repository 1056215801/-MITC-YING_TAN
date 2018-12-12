package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Dictionary;
import com.mit.community.entity.UserLabel;
import com.mit.community.module.system.mapper.UserLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    private final DictionaryService dictionaryService;

    @Autowired
    public UserLabelService(UserLabelMapper userLabelMapper, DictionaryService dictionaryService) {
        this.userLabelMapper = userLabelMapper;
        this.dictionaryService = dictionaryService;
    }


    /**
     * 查询用户标签，通过用户id
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.UserLabel>
     * @author shuyy
     * @date 2018/12/12 16:57
     * @company mitesofor
    */
    public List<UserLabel> listByUserId(Integer userId){
        EntityWrapper<UserLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserLabel> userLabels = userLabelMapper.selectList(wrapper);
        userLabels.forEach(item -> {
            String labelCode = item.getLabelCode();
            Dictionary dictionary = dictionaryService.getByCode(labelCode);
            item.setLableName(dictionary.getName());
        });
        return userLabels;
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
