package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.SelectionActivitiesContent;
import com.mit.community.mapper.SelectionActivitiesContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 精品活动详情业务处理层
 * @author Mr.Deng
 * @date 2018/12/19 20:43
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class SelectionActivitiesContentService {
    @Autowired
    private SelectionActivitiesContentMapper selectionActivitiesContentMapper;

    /**
     * 查询精品活动详情信息
     * @param selectionActivitiesId 精品活动id
     * @return
     * @author Mr.Deng
     * @date 20:47 2018/12/19
     */
    public SelectionActivitiesContent getByselectionActivitiesId(Integer selectionActivitiesId) {
        EntityWrapper<SelectionActivitiesContent> wrapper = new EntityWrapper<>();
        wrapper.eq("selection_activities_id", selectionActivitiesId);
        List<SelectionActivitiesContent> selectionActivitiesContents = selectionActivitiesContentMapper.selectList(wrapper);
        if (selectionActivitiesContents.isEmpty()) {
            return null;
        }
        return selectionActivitiesContents.get(0);
    }

}
