package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.SysMessageRead;
import com.mit.community.mapper.SysMessageReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统消息已读
 * @author shuyy
 * @date 2018/12/29
 * @company mitesofor
 */
@Service
public class SysMessageReadService extends ServiceImpl<SysMessageReadMapper, SysMessageRead> {

    @Autowired
    private SysMessageReadMapper sysMessageReadMapper;

    /**
     * 查询列表
     * @param userId userId
     * @return java.util.List<com.mit.community.entity.SysMessageRead>
     * @author shuyy
     * @date 2018/12/29 10:12
     * @company mitesofor
     */
    public List<SysMessageRead> listByUserId(Integer userId) {
        EntityWrapper<SysMessageRead> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return sysMessageReadMapper.selectList(wrapper);
    }

    /**
     * 批量保存已读信息
     * @param userId
     * @param sysMessageIdList
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/29 9:54
     * @company mitesofor
     */
    public void saveNotRead(Integer userId, List<Integer> sysMessageIdList) {
        List<SysMessageRead> sysMessageReads = this.listByUserId(userId);
        Map<Integer, SysMessageRead> map = Maps.newHashMapWithExpectedSize(sysMessageReads.size());
        sysMessageReads.forEach(item -> map.put(item.getSysMessageId(), item));
        List<SysMessageRead> sysMessageReadAdd = Lists.newArrayListWithCapacity(sysMessageIdList.size());
        sysMessageIdList.forEach(item -> {
            if (!map.containsKey(item)) {
                SysMessageRead sysMessageRead = new SysMessageRead(userId, item);
                sysMessageRead.setGmtCreate(LocalDateTime.now());
                sysMessageRead.setGmtModified(LocalDateTime.now());
                sysMessageReadAdd.add(sysMessageRead);
            }
        });
        if (!sysMessageReadAdd.isEmpty()) {
            this.insertBatch(sysMessageReadAdd);
        }
    }

    /**
     * 统计已读数量
     * @param userId
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/12/29 10:08
     * @company mitesofor
     */
    public Integer countNum(Integer userId) {
        EntityWrapper<SysMessageRead> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return sysMessageReadMapper.selectCount(wrapper);

    }

}
