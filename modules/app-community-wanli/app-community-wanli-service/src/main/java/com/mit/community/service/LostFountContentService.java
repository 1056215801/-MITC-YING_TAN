package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.LostFountContent;
import com.mit.community.mapper.LostFountContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 失物招领-失物详情
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
}
