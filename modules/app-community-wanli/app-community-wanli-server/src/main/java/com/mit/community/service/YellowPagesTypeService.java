package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.YellowPages;
import com.mit.community.entity.YellowPagesType;
import com.mit.community.mapper.YellowPagesTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 黄页类型业务处理层
 * @author Mr.Deng
 * @date 2018/12/6 20:22
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class YellowPagesTypeService {
    @Autowired
    private YellowPagesTypeMapper yellowPagesTypeMapper;

    /**
     * 查询黄页信息，通过id
     * @param id id
     * @return 黄页信息
     * @author Mr.Deng
     * @date 20:33 2018/12/6
     */
    public YellowPagesType getById(Integer id) {
        return yellowPagesTypeMapper.selectById(id);
    }

    /**
     * 查询父菜单
     * @return 父菜单集合
     * @author Mr.Deng
     * @date 19:33 2018/12/6
     */
    public List<Map<String, Object>> listToParentName() {
        EntityWrapper<YellowPagesType> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("parent_name");
        wrapper.groupBy("parent_name");
        return yellowPagesTypeMapper.selectMaps(wrapper);
    }

    /**
     * 查询所有的黄页类型菜单
     * @return 黄页类型菜单列表
     * @author Mr.Deng
     * @date 20:44 2018/12/6
     */
    public List<YellowPagesType> list() {
        return yellowPagesTypeMapper.selectList(null);
    }

    /**
     * 查询子菜单，通过父菜单
     * @param parentName 父菜单名称
     * @return 子菜单集合
     * @author Mr.Deng
     * @date 19:40 2018/12/6
     */
    public List<Map<String, Object>> listToSubmenuName(String parentName) {
        EntityWrapper<YellowPagesType> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id,submenu_name,image");
        wrapper.eq("parent_name", parentName);
        return yellowPagesTypeMapper.selectMaps(wrapper);
    }
}
