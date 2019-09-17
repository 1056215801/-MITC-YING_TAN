package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Equipment;
import com.mit.community.entity.PosInfo;
import com.mit.community.mapper.EquipmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qsj
 * @since 2019-09-11
 */
@Service
public class EquipmentService extends ServiceImpl<EquipmentMapper, Equipment> {

    public Page<Equipment> getPageList(String departmentNo, String serialNumber, String deviceHolder, String job, Integer pageNum, Integer pageSize, List<Map> list) {
        Page<Equipment> page=new Page<>(pageNum,pageSize);
        EntityWrapper<Equipment> wrapper=new EntityWrapper<>();
        if (StringUtils.isNotEmpty(departmentNo)){
            wrapper.like("department_no",departmentNo);
        }
        if (StringUtils.isNotEmpty(serialNumber))
        {
            wrapper.eq("serial_number",serialNumber);
        }
        if (StringUtils.isNotEmpty(deviceHolder)){
            wrapper.like("device_holder",deviceHolder);
        }
        if (StringUtils.isNotEmpty(job))
        {
            wrapper.like("job",job);
        }
        wrapper.orderBy("gmtCreate",false);
        List<Equipment> equipmentList = this.selectList(wrapper);
       for (Equipment equipment : equipmentList) {
           for (Map map : list) {
               if (equipment.getUserId()==Long.valueOf(String.valueOf(map.get("userId")))){
                   equipment.setType(map.get("type").toString());
               }
           }
        }

        page.setRecords(equipmentList);
        return page;
    }
}
