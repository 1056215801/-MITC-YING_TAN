package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.mit.community.entity.Label;
import com.mit.community.entity.UserLabel;
import com.mit.community.mapper.UserLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户标签
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserLabelService extends ServiceImpl<UserLabelMapper, UserLabel> {

    private final UserLabelMapper userLabelMapper;

    private final DictionaryService dictionaryService;
    @Autowired
    private LabelService labelService;

    @Autowired
    public UserLabelService(UserLabelMapper userLabelMapper, DictionaryService dictionaryService) {
        this.userLabelMapper = userLabelMapper;
        this.dictionaryService = dictionaryService;
    }

    /**
     * 删除用户标签，通过用户id
     * @param userId 用户id
     * @author shuyy
     * @date 2018/12/19 19:23
     * @company mitesofor
    */
    public void removeByUserId(Integer userId){
        EntityWrapper<UserLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        userLabelMapper.delete(wrapper);
    }

    /**
     * 删除，通过labelId，和UserId
     * @param userId
     * @param labelId
     * @return void
     * @throws
     * @author shuyy
     * @date 2019-01-11 14:43
     * @company mitesofor
    */
    public void removeByUserIdAndLabelId(Integer userId, Integer labelId){
        EntityWrapper<UserLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("label_id", labelId);
        userLabelMapper.delete(wrapper);
    }



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
     * 查询用户标签关联
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.UserLabel>
     * @author shuyy
     * @date 2018/12/19 19:43
     * @company mitesofor
    */
    public List<UserLabel> listByUserId(Integer userId){
        EntityWrapper<UserLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserLabel> userLabelList = userLabelMapper.selectList(wrapper);
        List<Integer> list = userLabelList.parallelStream().map(UserLabel::getLabelId).collect(Collectors.toList());
        List<Label> labels = labelService.listByIdList(list);
        Map<Integer, Label> map = Maps.newHashMapWithExpectedSize(labels.size());
        labels.forEach(item -> map.put(item.getId(), item));
        userLabelList.forEach(item -> {
            Integer labelId = item.getLabelId();
            Label label = map.get(labelId);
            item.setLableName(label.getName());
        });
        return userLabelList;
    }



}
