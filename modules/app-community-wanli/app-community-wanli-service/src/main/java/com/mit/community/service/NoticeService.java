package com.mit.community.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.Notice;
import com.mit.community.entity.NoticeContent;
import com.mit.community.entity.NoticeReadUser;
import com.mit.community.mapper.NoticeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private NoticeReadUserService noticeReadUserService;
    @Autowired
    private UserTrackService userTrackService;
    @Autowired
    private DictionaryService dictionaryService;

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
     * @param notice 通知通告数据
     * @return 修改数据数量
     * @author Mr.Deng
     * @date 16:24 2018/12/3
     */
    public Integer update(Notice notice) {
        notice.setGmtModified(LocalDateTime.now());
        return noticeMapper.updateById(notice);
    }

    /**
     * 查询所有的通知通告信息
     * @return 通知通告信息列表
     * @author Mr.Deng
     * @date 16:26 2018/12/3
     */
    public List<Notice> list() {
        return noticeMapper.selectList(null);
    }

    /**
     * 查询通知信息，通过通知信息id
     * @param noticeId 通知信息id
     * @return
     * @author Mr.Deng
     * @date 11:54 2018/12/14
     */
    public Notice getByNoticeId(Integer noticeId) {
        return noticeMapper.selectById(noticeId);
    }

    /**
     * 查询通知详情信息
     * @param noticeId 通知信息id
     * @return 通知信息
     * @author Mr.Deng
     * @date 12:50 2018/12/14
     */
    public List<Object> getNoticInfoByNotiveId(Integer noticeId) {
        List<Object> list = Lists.newArrayListWithExpectedSize(2);
        Notice notice = this.getByNoticeId(noticeId);
        if (notice != null) {
            NoticeContent noticeContent = noticeContentService.getByNoticId(noticeId);
            list.add(notice);
            list.add(noticeContent);
        }
        return list;
    }

    /**
     * 发布通知通告
     * @param title     标题
     * @param code      类型(查询字典notice_type)
     * @param synopsis  简介
     * @param publisher 发布人
     * @param creator   创建人
     * @param content   发布内容
     * @author Mr.Deng
     * @date 10:31 2018/11/30
     */
    @Transactional(rollbackFor = Exception.class)
    public void releaseNotice(String title, String code, String synopsis,
                              String publisher, String creator, String content) {
        Notice notice = new Notice(title, code, LocalDateTime.now(), synopsis, publisher, creator, StringUtils.EMPTY);
        this.save(notice);
        NoticeContent noticeContent = new NoticeContent(notice.getId(), content);
        noticeContentService.save(noticeContent);
    }

    /**
     * 查询所有通知信息和读取状态
     * @param userId 用户id
     * @return 通知信息
     * @author Mr.Deng
     * @date 14:34 2018/12/14
     */
    public List<Map<String, Object>> listNotices(Integer userId) {
        List<Map<String, Object>> list = Lists.newArrayListWithExpectedSize(100);
        List<Notice> notices = this.list();
        if (!notices.isEmpty()) {
            for (Notice notice : notices) {
                Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
                NoticeReadUser noticeReadUser = noticeReadUserService.getNoticeReadUser(userId, notice.getId());
                if (noticeReadUser != null) {
                    map.put("status", true);
                } else {
                    map.put("status", false);
                }
                map.put("notices", notice);
                list.add(map);
            }
        }
        return list;
    }

}
