package com.mit.community.service;

import com.mit.community.entity.NoticeContent;
import com.mit.community.mapper.NoticeContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
