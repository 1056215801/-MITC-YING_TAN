package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mit.community.entity.Dictionary;
import com.mit.community.entity.UserLabel;
import com.mit.community.module.system.mapper.UserLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户标签
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserLabelService {
    @Autowired
    private UserLabelMapper userLabelMapper;
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 保存
     * @param userLabel userlabel
     * @date 2018/11/29 11:45
     * @company mitesofor
     */
    public void save(UserLabel userLabel) {
        userLabel.setGmtCreate(LocalDateTime.now());
        userLabel.setGmtModified(LocalDateTime.now());
        userLabelMapper.insert(userLabel);
    }

    /**
     * 查询标签信息
     * @param userId 用户id
     * @return 标签列表
     * @author Mr.Deng
     * @date 12:00 2018/12/13
     */
    public List<UserLabel> listByUserId(Integer userId) {
        EntityWrapper<UserLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return userLabelMapper.selectList(wrapper);
    }

    /**
     * 查询标签信息
     * @param userId 用户id
     * @return 标签信息
     * @author Mr.Deng
     * @date 13:36 2018/12/13
     */
    public List<String> listLabelByUserId(Integer userId) {
        List<String> list = Lists.newArrayListWithExpectedSize(10);
        List<UserLabel> userLabels = this.listByUserId(userId);
        if (!userLabels.isEmpty()) {
            for (UserLabel userLabel : userLabels) {
                Dictionary dictionary = dictionaryService.getByCode(userLabel.getLabelCode());
                if (dictionary != null) {
                    list.add(dictionary.getName());
                }
            }
        }
        return list;
    }

}
