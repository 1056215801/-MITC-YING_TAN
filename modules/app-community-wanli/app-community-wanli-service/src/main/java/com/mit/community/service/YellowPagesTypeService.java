package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.YellowPagesType;
import com.mit.community.mapper.YellowPagesTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<YellowPagesType> listByParentName(String parentName) {
        EntityWrapper<YellowPagesType> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_name", parentName);
        return yellowPagesTypeMapper.selectList(wrapper);
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
        wrapper.orderBy("LENGTH(submenu_name)");
        return yellowPagesTypeMapper.selectMaps(wrapper);
    }

    /**
     * 保存
     * @param parentName  父类型
     * @param image       图片地址
     * @param submenuName 子类型
     * @author shuyy
     * @date 2018/12/21 19:22
     * @company mitesofor
     */
    public void save(String parentName, String image, String submenuName) {
        YellowPagesType yellowPagesType = new YellowPagesType(parentName, image, submenuName);
        yellowPagesType.setGmtCreate(LocalDateTime.now());
        yellowPagesType.setGmtModified(LocalDateTime.now());
        yellowPagesTypeMapper.insert(yellowPagesType);
    }

    /**
     * 更新
     * @param id          id
     * @param parentName  父类型
     * @param image       图片地址
     * @param submenuName 子类型
     * @author shuyy
     * @date 2018/12/21 19:33
     * @company mitesofor
     */
    public void update(Integer id, String parentName, String image, String submenuName) {
        YellowPagesType yellowPagesType = new YellowPagesType();
        yellowPagesType.setId(id);
        if (StringUtils.isNotBlank(parentName)) {
            yellowPagesType.setParentName(parentName);
        }
        if (StringUtils.isNotBlank(image)) {
            yellowPagesType.setImage(image);
        }
        if (StringUtils.isNotBlank(submenuName)) {
            yellowPagesType.setSubmenuName(submenuName);
        }
        yellowPagesType.setGmtModified(LocalDateTime.now());
        yellowPagesTypeMapper.updateById(yellowPagesType);
    }

    /**
     * 删除
     * @param id id
     * @author shuyy
     * @date 2018/12/21 19:36
     * @company mitesofor
     */
    public void remove(Integer id) {
        yellowPagesTypeMapper.deleteById(id);
    }

    /**
     * 列表
     * @param pageNum  当前页
     * @param pageSize 分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.YellowPagesType>
     * @author shuyy
     * @date 2018/12/21 19:40
     * @company mitesofor
     */
    public Page<YellowPagesType> listPage(Integer pageNum, Integer pageSize) {
        Page<YellowPagesType> page = new Page<>(pageNum, pageSize);
        List<YellowPagesType> yellowPagesTypes = yellowPagesTypeMapper.selectPage(page, null);
        page.setRecords(yellowPagesTypes);
        return page;
    }
}
