package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.LostFountContent;
import com.mit.community.mapper.LostFountContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 失物招领-失物详情
 *
 * @author Mr.Deng
 * @date 2018/12/17 20:34
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class LostFountContentService {
    @Autowired
    private LostFountContentMapper lostFountContentMapper;

    /**
     * 查询失物招领详情信息，通过失物招领id
     *
     * @param lostFountId 失物招领id
     * @return 失物招领详情信息
     * @author Mr.Deng
     * @date 9:27 2018/12/18
     */
    public LostFountContent listByLostFountId(Integer lostFountId) {
        EntityWrapper<LostFountContent> wrapper = new EntityWrapper<>();
        wrapper.eq("lost_fount_id", lostFountId);
        List<LostFountContent> lostFountContents = lostFountContentMapper.selectList(wrapper);
        if (lostFountContents.isEmpty()) {
            return null;
        }
        return lostFountContents.get(0);
    }

    /**
     * 保存
     *
     * @param lostFoundContent
     * @author shuyy
     * @date 2018/12/27 10:05
     * @company mitesofor
     */
    public void save(LostFountContent lostFoundContent) {
        lostFoundContent.setGmtCreate(LocalDateTime.now());
        lostFoundContent.setGmtModified(LocalDateTime.now());
        lostFountContentMapper.insert(lostFoundContent);
    }

    /**
     * 更新，通过失物招领id
     *
     * @param lostFoundContent 内容
     * @author shuyy
     * @date 2018/12/27 10:59
     * @company mitesofor
     */
    public void updateByLostFoudId(LostFountContent lostFoundContent) {
        lostFoundContent.setGmtModified(LocalDateTime.now());
        EntityWrapper<LostFountContent> wrapper = new EntityWrapper<>();
        wrapper.eq("lost_fount_id", lostFoundContent.getLostFountId());
        lostFountContentMapper.update(lostFoundContent, wrapper);
    }

    /**
     * @param lostFoundId 失物招领id
     * @author shuyy
     * @date 2018/12/27 11:03
     * @company mitesofor
     */
    public void removeByLostFoudId(Integer lostFoundId) {
        EntityWrapper<LostFountContent> wrapper = new EntityWrapper<>();
        wrapper.eq("lost_fount_id", lostFoundId);
        lostFountContentMapper.delete(wrapper);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/23 13:23
     * @Company mitesofor
     * @Description:~根据id获取单个对象
     */
    public LostFountContent getObjectById(Integer id) {
        EntityWrapper<LostFountContent> wrapper = new EntityWrapper<>();
        wrapper.eq("lost_fount_id", id);
        List<LostFountContent> list = lostFountContentMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
