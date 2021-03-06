package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.LostFountReadUser;
import com.mit.community.mapper.LostFountReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 失物招领已读业务处理层
 * @author Mr.Deng
 * @date 2018/12/17 20:34
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class LostFountReadUserService {
    @Autowired
    private LostFountReadUserMapper lostFountReadUserMapper;

    /**
     * 添加已读信息
     * @param lostFountReadUser 已读信息
     * @author Mr.Deng
     * @date 9:47 2018/12/18
     */
    public void save(LostFountReadUser lostFountReadUser) {
        lostFountReadUser.setGmtCreate(LocalDateTime.now());
        lostFountReadUser.setGmtModified(LocalDateTime.now());
        lostFountReadUserMapper.insert(lostFountReadUser);
    }

    /**
     * 获取用户指定的已读记录
     * @param userId
     * @param lostFountId
     * @return com.mit.community.entity.LostFountReadUser
     * @throws
     * @author shuyy
     * @date 2018/12/29 10:33
     * @company mitesofor
     */
    public LostFountReadUser getByUserIdAndLostFountId(Integer userId, Integer lostFountId) {
        EntityWrapper<LostFountReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("lost_fount_id", lostFountId);
        List<LostFountReadUser> lostFountReadUsers = lostFountReadUserMapper.selectList(wrapper);
        if (lostFountReadUsers.isEmpty()) {
            return null;
        }
        return lostFountReadUsers.get(0);
    }

    /**
     * 查询失物招领已读信息
     * @param userId      用户id
     * @param lostFountId 失物招领id
     * @return 失物招领已读信息
     * @author Mr.Deng
     * @date 9:51 2018/12/18
     */
    public LostFountReadUser getByUserIdByLostFountId(Integer userId, Integer lostFountId) {
        EntityWrapper<LostFountReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("lost_fount_id", lostFountId);
        List<LostFountReadUser> lostFountReadUsers = lostFountReadUserMapper.selectList(wrapper);
        if (lostFountReadUsers.isEmpty()) {
            return null;
        }
        return lostFountReadUsers.get(0);
    }

    /**
     * 统计已读条数
     * @param userId 用户id
     * @return java.lang.Integer
     * @author shuyy
     * @date 2019-01-02 15:49
     * @company mitesofor
     */
    public Integer countByUserId(Integer userId) {
        EntityWrapper<LostFountReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return lostFountReadUserMapper.selectCount(wrapper);
    }
}
