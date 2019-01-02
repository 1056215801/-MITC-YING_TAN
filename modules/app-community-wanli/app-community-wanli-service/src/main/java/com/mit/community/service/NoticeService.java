package com.mit.community.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.mit.community.entity.Notice;
import com.mit.community.entity.NoticeContent;
import com.mit.community.entity.NoticeReadUser;
import com.mit.community.mapper.NoticeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Notice> listValidNoticeAndTime(String communityCode, LocalDate startDay, LocalDate endDay) {
        EntityWrapper<Notice> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.le("release_time", endDay);
        wrapper.ge("release_time", startDay);
        wrapper.ge("validate_time", LocalDateTime.now());
        wrapper.orderBy("release_time", false);
        return noticeMapper.selectList(wrapper);
    }

    /**
     * 查询通知信息，通过通知信息id
     * @param noticeId 通知信息id
     * @return notic
     * @author Mr.Deng
     * @date 11:54 2018/12/14
     */
    public Notice getByNoticeId(Integer noticeId) {
        return noticeMapper.selectById(noticeId);
    }

    /**
     * 获取该小区的通知通告总数
     * @param communityCode 小区code
     * @return 总条数
     * @author Mr.Deng
     * @date 16:08 2018/12/28
     */
    public Integer getByCommunityCode(String communityCode) {
        EntityWrapper<Notice> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return noticeMapper.selectCount(wrapper);
    }

    /**
     * 查询通知详情信息
     * @param noticeId 通知信息id
     * @return 通知信息
     * @author Mr.Deng
     * @date 12:50 2018/12/14
     */
    public Notice getNoticeInfoByNoticeId(Integer noticeId) {
        Notice notice = this.getByNoticeId(noticeId);
        if (notice != null) {
            NoticeContent noticeContent = noticeContentService.getByNoticId(noticeId);
            Integer viewNum = noticeReadUserService.countViewNum(noticeId);
            notice.setContent(noticeContent == null ? StringUtils.EMPTY : noticeContent.getContent());
            notice.setViews(viewNum);
        }
        return notice;
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
    public void releaseNotice(String communityCode, String title, String code, String synopsis,
                              String publisher, Integer creator, String content, LocalDateTime releaseTime, LocalDateTime validateTime) {
        Notice notice = new Notice(communityCode, title, code, releaseTime, validateTime, synopsis, publisher, creator, 0, null, null, null);
        this.save(notice);
        NoticeContent noticeContent = new NoticeContent(notice.getId(), content);
        noticeContentService.save(noticeContent);
    }

    /**
     * @param id        id
     * @param title     标题
     * @param code      类型(查询字典notice_type)
     * @param synopsis  简介
     * @param publisher 发布人
     * @param modifier  修改人
     * @param content   发布内容
     * @author shuyy
     * @date 2018/12/26 9:31
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateNotice(Integer id, String title, String code, String synopsis,
                             String publisher, Integer modifier, String content, LocalDateTime releaseTime, LocalDateTime validateTime) {
        Notice notice = new Notice();
        notice.setId(id);
        if (StringUtils.isNotBlank(title)) {
            notice.setTitle(title);
        }
        if (StringUtils.isNotBlank(code)) {
            notice.setTitle(code);
        }
        if (StringUtils.isNotBlank(synopsis)) {
            notice.setSynopsis(synopsis);
        }
        if (StringUtils.isNotBlank(publisher)) {
            notice.setPublisher(publisher);
        }
        notice.setModifier(modifier);
        noticeMapper.updateById(notice);
        if (StringUtils.isNotBlank(content)) {
            noticeContentService.updateByNoticeId(id, content);
        }
    }

    /**
     * 删除
     * @param noticeId 通知通告id
     * @author shuyy
     * @date 2018/12/26 9:39
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer noticeId) {
        noticeMapper.deleteById(noticeId);
        noticeContentService.removeByNoticeId(noticeId);
    }

    /**
     * 查询所有通知信息和读取状态
     * @param userId 用户id
     * @return 通知信息
     * @author Mr.Deng
     * @date 14:34 2018/12/14
     */
    public List<Notice> listNoticesAndTime(String communityCode, Integer userId, LocalDate startDay, LocalDate endDay) {
        List<Notice> notices = this.listValidNoticeAndTime(communityCode, startDay, endDay);
        if (!notices.isEmpty()) {
            Map<Integer, Notice> map = Maps.newHashMapWithExpectedSize(notices.size());
            List<Integer> noticeIdList = notices.parallelStream().map(Notice::getId).collect(Collectors.toList());
            notices.forEach(item -> {
                item.setReadStatus(false);
                map.put(item.getId(), item);
            });
            List<NoticeReadUser> noticeReadUsers = noticeReadUserService.listNoticeReadUserByUserIdAndNoticeIdList(userId, noticeIdList);
            if (!noticeReadUsers.isEmpty()) {
                noticeReadUsers.forEach(item -> {
                    Integer noticeId = item.getNoticeId();
                    Notice notice = map.get(noticeId);
                    notice.setReadStatus(true);
                });
            }
        }
        return notices;
    }

    /**
     * 分页查询
     * @param communityCode     小区code
     * @param releaseTimeStart  发布开始时间
     * @param releaseTimeEnd    发布结束时间
     * @param validateTimeStart 过期开始时间
     * @param validateTimeEnd   过期结束时间
     * @param publisher         发布人
     * @param pageNum           当前页
     * @param pageSize          分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.Notice>
     * @author shuyy
     * @date 2018/12/26 11:17
     * @company mitesofor
     */
    public Page<Notice> listPage(String communityCode, LocalDateTime releaseTimeStart,
                                 LocalDateTime releaseTimeEnd,
                                 LocalDateTime validateTimeStart,
                                 LocalDateTime validateTimeEnd, String publisher, Integer pageNum, Integer pageSize) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        EntityWrapper<Notice> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (releaseTimeStart != null) {
            wrapper.ge("release_time", releaseTimeStart);
        }
        if (releaseTimeEnd != null) {
            wrapper.le("release_time", releaseTimeEnd);
        }
        if (validateTimeStart != null) {
            wrapper.ge("validate_time", releaseTimeStart);
        }
        if (validateTimeEnd != null) {
            wrapper.le("validate_time", validateTimeEnd);
        }
        if (StringUtils.isNotBlank(publisher)) {
            wrapper.like("publisher", publisher, SqlLike.RIGHT);
        }
        List<Notice> notices = noticeMapper.selectPage(page, wrapper);
        page.setRecords(notices);
        return page;
    }

    /**
     * 未读数
     * @param communityCode 小区code
     * @param userId        用户id
     * @return 未读数
     * @author Mr.Deng
     * @date 16:31 2018/12/28
     */
    public Integer getNotReadNotice(String communityCode, Integer userId) {
        Integer size = null;
        List<NoticeReadUser> noticeReadUsers = noticeReadUserService.getReadNoticByUserIdAndNoticId(userId);
        if (!noticeReadUsers.isEmpty()) {
            int noticeReadSize = noticeReadUsers.size();
            Integer noticeSize = this.getByCommunityCode(communityCode);
            if (noticeSize == 0 || noticeReadSize > noticeSize) {
                size = 0;
            } else {
                size = noticeSize - noticeReadSize;
            }
        }
        return size;
    }

}
