package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.NoticeReadUser;
import com.mit.community.mapper.NoticeReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知已读人员业务处理层
 * @author Mr.Deng
 * @date 2018/12/14 10:35
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class NoticeReadUserService {
    @Autowired
    private NoticeReadUserMapper noticeReadUserMapper;

    /**
     * 添加通知已读信息
     * @param noticeReadUser 通知已读信息
     * @author Mr.Deng
     * @date 10:36 2018/12/14
     */
    public void save(NoticeReadUser noticeReadUser) {
        NoticeReadUser item = this.getNoticeReadUser(noticeReadUser.getUserId(), noticeReadUser.getUserId());
        if(item == null){
            noticeReadUser.setGmtCreate(LocalDateTime.now());
            noticeReadUser.setGmtModified(LocalDateTime.now());
            noticeReadUserMapper.insert(noticeReadUser);
        }
    }

    /**
     * 查询相应的通知信息浏览量，通告通知id
     * @param noticeId 通知id
     * @return 浏览量
     * @author Mr.Deng
     * @date 14:07 2018/12/14
     */
    public Integer countViewNum(Integer noticeId) {
        EntityWrapper<NoticeReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("notice_id", noticeId);
        return noticeReadUserMapper.selectCount(wrapper);
    }

    /**
     * 查询通知阅读信息，通过用户id和通知id
     * @param userId   用户id
     * @param noticeId 通知id
     * @return 通知阅读信息
     * @author Mr.Deng
     * @date 14:24 2018/12/14
     */
    public NoticeReadUser getNoticeReadUser(Integer userId, Integer noticeId) {
        EntityWrapper<NoticeReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("notice_id", noticeId);
        List<NoticeReadUser> noticeReadUsers = noticeReadUserMapper.selectList(wrapper);
        if (noticeReadUsers.isEmpty()) {
            return null;
        }
        return noticeReadUsers.get(0);
    }

    /**
     * 查询已读信息，通过用户id
     * @param userId 用户id
     * @return 已读数列表
     * @author Mr.Deng
     * @date 16:20 2018/12/28
     */
    public List<NoticeReadUser> getReadNoticByUserIdAndNoticId(Integer userId) {
        EntityWrapper<NoticeReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.groupBy("notice_id");
        return noticeReadUserMapper.selectList(wrapper);
    }

    /**
     * 查询通知阅读信息，通过用户id和通知id
     * @param userId       用户id
     * @param noticeIdList 通知id
     * @return 通知阅读信息
     * @author Mr.Deng
     * @date 14:24 2018/12/14
     */
    public List<NoticeReadUser> listNoticeReadUserByUserIdAndNoticeIdList(Integer userId, List<Integer> noticeIdList) {
        EntityWrapper<NoticeReadUser> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.in("notice_id", noticeIdList);
        return noticeReadUserMapper.selectList(wrapper);
    }
}
