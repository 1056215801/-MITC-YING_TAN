package com.mit.community.service;

import com.mit.community.entity.Notice;
import com.mit.community.entity.NoticeContent;
import com.mit.community.mapper.NoticeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知通告业务表
 * @author Mr.Deng
 * @date 2018/12/3 14:36
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeContentService noticeContentService;

    /**
     * 添加通知通告数据
     * @param notice 通知通告数据
     * @author Mr.Deng
     * @date 16:29 2018/11/29
     */
    public Integer save(Notice notice) {
        notice.setGmtModified(LocalDateTime.now());
        notice.setGmtCreate(LocalDateTime.now());
        return noticeMapper.insert(notice);
    }

    /**
     * 修改通知通告数据
     * @param notice
     * @return
     * @author Mr.Deng
     * @date 16:24 2018/12/3
     */
    public Integer update(Notice notice) {
        notice.setGmtModified(LocalDateTime.now());
        return noticeMapper.updateById(notice);
    }

    /**
     * 查询所有的通知通告信息
     * @return
     * @author Mr.Deng
     * @date 16:26 2018/12/3
     */
    public List<Notice> list() {
        return noticeMapper.selectList(null);
    }

    /**
     * 发布通知通告
     * @param title     标题
     * @param type      类型
     * @param synopsis  简介
     * @param publisher 发布人
     * @param creator   创建人
     * @param content   发布内容
     * @author Mr.Deng
     * @date 10:31 2018/11/30
     */
    @Transactional(rollbackFor = Exception.class)
    public void releaseNotice(String title, String type, String typeName, String synopsis,
                              String publisher, String creator, String content) {
        Notice notice = new Notice(title, type, typeName, LocalDateTime.now(), synopsis, publisher, creator, StringUtils.EMPTY);
        Integer save = this.save(notice);
        if (save > 0) {
            NoticeContent noticeContent = new NoticeContent(notice.getId(), content);
            noticeContentService.save(noticeContent);
        }
    }



}
