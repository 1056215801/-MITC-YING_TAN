package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.YellowPages;
import com.mit.community.entity.YellowPagesType;
import com.mit.community.mapper.YellowPagesMapper;
import com.mit.community.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 生活黄页业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 17:15
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class YellowPagesService {
    @Autowired
    private YellowPagesMapper yellowPagesMapper;

    @Autowired
    private YellowPagesTypeService yellowPagesTypeService;

    /**
     * 查询生活黄页信息，通过黄页类型id
     * @param yellowPagesTypeId 黄页类型id
     * @return 生活黄页信息
     * @author Mr.Deng
     * @date 17:19 2018/12/5
     */
    public List<YellowPages> listByYellowPagesTypeId(Integer yellowPagesTypeId) {
        EntityWrapper<YellowPages> wrapper = new EntityWrapper<>();
        wrapper.eq("yellow_pages_type_id", yellowPagesTypeId);
        return yellowPagesMapper.selectList(wrapper);
    }

    /**
     * 获取所有的黄页菜单信息
     * @return 黄页菜单信息
     * @author Mr.Deng
     * @date 19:45 2018/12/6
     */
    public List<Object> listAllYellowPages() {
        List<Object> list = Lists.newArrayListWithCapacity(30);
        List<Map<String, Object>> parentNames = yellowPagesTypeService.listToParentName();
        if (!parentNames.isEmpty()) {
            for (Map<String, Object> map : parentNames) {
                String parentName = map.get("parent_name").toString();
                List<Map<String, Object>> submenuNames = yellowPagesTypeService.listToSubmenuName(parentName);
                map.put("submenuNames", submenuNames);
            }
            list.add(parentNames);
        }
        return list;
    }

    /**
     * 查询黄页号码
     * @param yellowPagesTypeId 黄页类型id
     * @return 黄页号码
     * @author Mr.Deng
     * @date 20:40 2018/12/6
     */
    public Map<String, Object> mapToPhoneByYellowPagesTypeId(Integer yellowPagesTypeId) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(30);
        YellowPagesType yellowPagesType = yellowPagesTypeService.getById(yellowPagesTypeId);
        if (yellowPagesType != null) {
            map.put("image", yellowPagesType.getImage());
            map.put("submenuName", yellowPagesType.getSubmenuName());
            List<YellowPages> yellowPages = this.listByYellowPagesTypeId(yellowPagesTypeId);
            map.put("phone", yellowPages);
        }
        return map;
    }

    /**
     * 查询所有的黄页号码
     * @return 黄页号码列表
     * @author Mr.Deng
     * @date 20:51 2018/12/6
     */
    public List<Map<String, Object>> listToPhone() {
        List<Map<String, Object>> list = Lists.newArrayListWithExpectedSize(100);
        List<YellowPagesType> yellowPagesTypes = yellowPagesTypeService.list();
        if (!yellowPagesTypes.isEmpty()) {
            for (YellowPagesType yellowPagesType : yellowPagesTypes) {
                Map<String, Object> map = Maps.newHashMapWithExpectedSize(30);
                map.put("image", yellowPagesType.getImage());
                map.put("submenuName", yellowPagesType.getSubmenuName());
                List<YellowPages> yellowPages = this.listByYellowPagesTypeId(yellowPagesType.getId());
                map.put("phone", yellowPages);
                list.add(map);
            }
        }
        return list;
    }

}
