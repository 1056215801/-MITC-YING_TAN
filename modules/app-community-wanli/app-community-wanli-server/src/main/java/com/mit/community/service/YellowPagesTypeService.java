package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.YellowPagesType;
import com.mit.community.mapper.YellowPagesTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 生活黄页类型业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 17:11
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class YellowPagesTypeService {
    @Autowired
    private YellowPagesTypeMapper yellowPagesTypeMapper;

    /**
     * 查询所有的父菜单
     * @return
     * @author Mr.Deng
     * @date 17:26 2018/12/5
     */
    public List<Map<String, Object>> listToParentName() {
        EntityWrapper<YellowPagesType> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("parent_name");
        wrapper.groupBy("parent_name");
        return yellowPagesTypeMapper.selectMaps(wrapper);
    }

    /**
     * 查询子菜单，通过父菜单
     * @param parentName 父菜单
     * @return 子菜单列表
     * @author Mr.Deng
     * @date 17:29 2018/12/5
     */
    public List<Map<String, Object>> listByParentName(String parentName) {
        EntityWrapper<YellowPagesType> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id,submenu_name");
        wrapper.eq("parent_name", parentName);
        return yellowPagesTypeMapper.selectMaps(wrapper);
    }

    /**
     * 查询所有的菜单信息
     * @return 菜单信息列表
     * @author Mr.Deng
     * @date 17:31 2018/12/5
     */
    public List<Object> listAllYellowPages() {
        List<Object> lists = Lists.newArrayListWithExpectedSize(15);
        List<Map<String, Object>> parentNames = this.listToParentName();
        if (!parentNames.isEmpty()) {
            lists.add(parentNames);
            for (Map<String, Object> parentNameMap : parentNames) {
                String parentName = parentNameMap.get("parent_name").toString();
                List<Map<String, Object>> list1 = this.listByParentName(parentName);
                parentNameMap.put("listSubmenuName", list1);
                lists.add(parentNameMap);
            }
        }
        return lists;
    }

}
