package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.SelectionActivities;
import com.mit.community.entity.SelectionActivitiesContent;
import com.mit.community.mapper.SelectionActivitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 精选活动业务处理层
 * @author Mr.Deng
 * @date 2018/12/19 20:42
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class SelectionActivitiesService extends ServiceImpl<SelectionActivitiesMapper, SelectionActivities> {
    @Autowired
    private SelectionActivitiesMapper selectionActivitiesMapper;
    @Autowired
    private SelectionActivitiesContentService selectionActivitiesContentService;

    /**
     * 查询精品活动信息列表
     * @return 精品活动信息
     * @author Mr.Deng
     * @date 14:03 2018/12/22
     */
    public List<SelectionActivities> list() {
        EntityWrapper<SelectionActivities> wrapper = new EntityWrapper<>();
        wrapper.orderBy("issue_time", false);
        wrapper.ge("valid_time", LocalDateTime.now());
        return selectionActivitiesMapper.selectList(wrapper);
    }

    /**
     * 查询精品活动信息，通过id
     * @param id id
     * @return 精品活动信息
     * @author Mr.Deng
     * @date 14:04 2018/12/22
     */
    public SelectionActivities getById(Integer id) {
        return selectionActivitiesMapper.selectById(id);
    }

    /**
     * 更新精品活动数据数据
     * @param selectionActivities 精品活动数据
     * @author Mr.Deng
     * @date 14:10 2018/12/22
     */
    public void update(SelectionActivities selectionActivities) {
        selectionActivitiesMapper.updateById(selectionActivities);
    }

    /**
     * 增加浏览量信息
     * @param selectionActivities 精品活动信息
     * @author Mr.Deng
     * @date 15:18 2018/12/22
     */
    public void AddSelectionActivitiesReadNum(SelectionActivities selectionActivities) {
        Integer num = this.updateReadNum(selectionActivities);
        if (num == 0) {
            selectionActivities = this.getById(selectionActivities.getId());
            if (selectionActivities == null) {
                return;
            } else {
                this.updateReadNum(selectionActivities);
            }
        }
        return;
    }

    /**
     * 记录浏览量
     * @param selectionActivities 精品活动
     * @author Mr.Deng
     * @date 14:34 2018/12/22
     */
    public Integer updateReadNum(SelectionActivities selectionActivities) {
        Integer readNum = selectionActivities.getReadNum();
        selectionActivities.setReadNum(readNum + 1);
        EntityWrapper<SelectionActivities> wrapper = new EntityWrapper<>();
        wrapper.eq("read_num", readNum).eq("id", selectionActivities.getId());
        Integer update = selectionActivitiesMapper.update(selectionActivities, wrapper);
        return update;
    }

    /**
     * 查询精品活动详情信息，通过精品活动信息
     * @param selectionActivitiesId 精品活动信息id
     * @return 精品活动给详情信息
     * @author Mr.Deng
     * @date 14:07 2018/12/22
     */
    public SelectionActivities getBySelectionActivitiesId(Integer selectionActivitiesId) {
        SelectionActivities selectionActivities = this.getById(selectionActivitiesId);
        SelectionActivitiesContent selectionActivitiesContent = selectionActivitiesContentService.getByselectionActivitiesId(selectionActivitiesId);
        if (selectionActivitiesContent != null) {
            selectionActivities.setContent(selectionActivitiesContent.getContent());
        }
        return selectionActivities;
    }

}
