package com.mit.community.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.constants.Constants;
import com.mit.community.entity.Label;
import com.mit.community.entity.UserLabel;
import com.mit.community.mapper.LabelMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签
 *
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@Service
public class LabelService extends ServiceImpl<LabelMapper, Label> {
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private UserLabelService userLabelService;

    /**
     * 保存
     *
     * @param name   姓名
     * @param type   标签id。关联label表id字段。
     * @param userId userId
     * @author shuyy
     * @date 2018/12/19 18:22
     * @company mitesofor
     */
    public Label save(String name, Short type, Integer userId) {
        Label label = new Label(name, type, userId, null);
        label.setGmtCreate(LocalDateTime.now());
        label.setGmtModified(LocalDateTime.now());
        labelMapper.insert(label);
        return label;
    }

    /**
     * 查询标签，通过用户id和标签名
     * @param name
     * @param userId
     * @return java.util.List<com.mit.community.entity.Label>
     * @throws
     * @author shuyy
     * @date 2019-01-11 12:00
     * @company mitesofor
    */
    public List<Label> listByUserIdAndName(String name, Integer userId){
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("name", name);
        return labelMapper.selectList(wrapper);
    }

    /**
     * 查询自定义标签列表，通过标签名列表和用户id
     * @param idList 标签名列表
     * @param userId 用户id
     * @return com.mit.community.entity.Label
     * @author shuyy
     * @date 2018/12/19 19:12
     * @company mitesofor
    */
    public List<Label> listByNameListAndUserId(List<Integer> idList, Integer userId) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.in("id", idList).eq("user_id", userId);
        return labelMapper.selectList(wrapper);
    }

    /**
     * 删除标签，通过id
     *
     * @param id 标签id
     * @author shuyy
     * @date 2018/12/19 18:31
     * @company mitesofor
     */
    public void removeById(Integer id) {
        labelMapper.deleteById(id);
    }

    /**
     * 删除，通过用户id和标签名
     *
     * @param userId 用户id
     * @param userId   标签id
     * @author shuyy
     * @date 2018/12/19 19:04
     * @company mitesofor
     */
    public void removeByLabelIdAndUserId(Integer labelId, Integer userId) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("id", labelId);
        labelMapper.delete(wrapper);
        userLabelService.removeByUserIdAndLabelId(userId, labelId);
    }

    /**
     * 查询标签列表，通过类型
     *
     * @param type 标签类型
     * @return java.util.List<com.mit.community.entity.Label>
     * @author shuyy
     * @date 2018/12/19 18:40
     * @company mitesofor
     */
    public List<Label> listByType(Short type, Integer userId) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("type", type);
        if(type == Constants.LABEL_TYPE_CUSTOMER){
            wrapper.eq("user_id", userId);
        }
        return labelMapper.selectList(wrapper);
    }

    /**
     * 查询标签，分页
     *
     * @param type     类型
     * @param name     标签名
     * @param pageNum  当前页
     * @param pageSize 分页大小
     * @return java.util.List<com.mit.community.entity.Label>
     * @author shuyy
     * @date 2018/12/19 18:44
     * @company mitesofor
     */
    public List<Label> listPage(Short type, String name, Integer pageNum, Integer pageSize) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name, SqlLike.RIGHT);
        }
        Page<Label> page = new Page<>();
        return labelMapper.selectPage(page, wrapper);
    }

    /**
     * 查询用户自定标签，通过用户id
     *
     * @param userId
     * @return java.util.List<com.mit.community.entity.Label>
     * @throws
     * @author shuyy
     * @date 2018/12/19 18:51
     * @company mitesofor
     */
    public List<Label> listByUserId(Integer userId) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return labelMapper.selectList(wrapper);
    }

    /**
     * 查询用户关联的所有标签
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.Label>
     * @author shuyy
     * @date 2018/12/19 19:45
     * @company mitesofor
    */
    public List<Label> listAssociationLabelByUserId(Integer userId){
        List<UserLabel> userLabelList = userLabelService.listByUserId(userId);
        if(userLabelList.isEmpty()){
            return Lists.newArrayListWithCapacity(0);
        }
        List<Integer> labelIdList = userLabelList.parallelStream().map(UserLabel::getLabelId).collect(Collectors.toList());
        return this.selectBatchIds(labelIdList);
    }

    /**
     * 查询label，通过labelid列表
     * @param labelIds
     * @return java.util.List<com.mit.community.entity.Label>
     * @throws
     * @author shuyy
     * @date 2019-01-04 10:00
     * @company mitesofor
    */
    public List<Label> listByIdList(List<Integer> labelIds){
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.in("id", labelIds);
        return labelMapper.selectList(wrapper);
    }


}
