package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.NoticeContent;
import com.mit.community.mapper.NoticeContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知通告内容业务处理层
 * @author Mr.Deng
 * @date 2018/12/3 14:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class NoticeContentService {

    @Autowired
    private NoticeContentMapper noticeContentMapper;

    /**
     * 添加通知内容数据
     * @param noticeContent 通知内容数据
     * @return 添加数据条数
     * @author Mr.Deng
     * @date 9:41 2018/11/30
     */
    public Integer save(NoticeContent noticeContent) {
        noticeContent.setGmtCreate(LocalDateTime.now());
        noticeContent.setGmtModified(LocalDateTime.now());
        return noticeContentMapper.insert(noticeContent);
    }

    /**
     * 查找通知内容,通过通知id
     * @param noticId 通知id
     * @return
     * @author Mr.Deng
     * @date 11:58 2018/12/14
     */
    public NoticeContent getByNoticId(Integer noticId) {
        EntityWrapper<NoticeContent> wrapper = new EntityWrapper<>();
        wrapper.eq("notice_id", noticId);
        List<NoticeContent> noticeContents = noticeContentMapper.selectList(wrapper);
        if (noticeContents.isEmpty()) {
            return null;
        }
        return noticeContents.get(0);
    }
}
