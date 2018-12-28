package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.SelectionActivities;
import com.mit.community.entity.SelectionActivitiesContent;
import com.mit.community.mapper.SelectionActivitiesMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 精选活动业务处理层
 * @author Mr.Deng
 * @date 2018/12/19 20:42
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class SelectionActivitiesService extends ServiceImpl<SelectionActivitiesMapper, SelectionActivities> {
    @Autowired
    private SelectionActivitiesMapper selectionActivitiesMapper;
    @Autowired
    private SelectionActivitiesContentService selectionActivitiesContentService;

    /**
     * 查询精品活动信息列表
     * @return 精品活动信息
     * @author Mr.Deng
     * @date 14:03 2018/12/22
     */
    public List<SelectionActivities> list() {
        EntityWrapper<SelectionActivities> wrapper = new EntityWrapper<>();
        wrapper.orderBy("issue_time", false);
        wrapper.ge("valid_time", LocalDateTime.now());
        return selectionActivitiesMapper.selectList(wrapper);
    }

    /**
     * 查询精品活动信息，通过id
     * @param id id
     * @return 精品活动信息
     * @author Mr.Deng
     * @date 14:04 2018/12/22
     */
    public SelectionActivities getById(Integer id) {
        return selectionActivitiesMapper.selectById(id);
    }

    /**
     * 更新精品活动数据数据
     * @param selectionActivities 精品活动数据
     * @author Mr.Deng
     * @date 14:10 2018/12/22
     */
    public void update(SelectionActivities selectionActivities) {
        selectionActivitiesMapper.updateById(selectionActivities);
    }

    /**
     * 增加浏览量信息
     * @param selectionActivities 精品活动信息
     * @author Mr.Deng
     * @date 15:18 2018/12/22
     */
    public void AddSelectionActivitiesReadNum(SelectionActivities selectionActivities) {
        Integer num = this.updateReadNum(selectionActivities);
        if (num == 0) {
            selectionActivities = this.getById(selectionActivities.getId());
            if (selectionActivities == null) {
                return;
            } else {
                this.updateReadNum(selectionActivities);
            }
        }
        return;
    }

    /**
     * 记录浏览量
     * @param selectionActivities 精品活动
     * @author Mr.Deng
     * @date 14:34 2018/12/22
     */
    public Integer updateReadNum(SelectionActivities selectionActivities) {
        selectionActivities.getId();
        Integer readNum = selectionActivities.getReadNum();
        selectionActivities.setReadNum(readNum + 1);
        EntityWrapper<SelectionActivities> wrapper = new EntityWrapper<>();
        wrapper.eq("read_num", readNum).eq("id", selectionActivities.getId());
        Integer update = selectionActivitiesMapper.update(selectionActivities, wrapper);
        return update;
    }

    /**
     * 查询精品活动详情信息，通过精品活动信息
     * @param selectionActivitiesId 精品活动信息id
     * @return 精品活动给详情信息
     * @author Mr.Deng
     * @date 14:07 2018/12/22
     */
    public SelectionActivities getBySelectionActivitiesId(Integer selectionActivitiesId) {
        SelectionActivities selectionActivities = this.getById(selectionActivitiesId);
        SelectionActivitiesContent selectionActivitiesContent = selectionActivitiesContentService.getByselectionActivitiesId(selectionActivitiesId);
        if (selectionActivitiesContent != null) {
            selectionActivities.setContent(selectionActivitiesContent.getContent());
        }
        return selectionActivities;
    }

    /**
     * 保存
     * @param title       标题
     * @param introduce   简介
     * @param externalUrl 外接地址
     * @param validTime   有效期
     * @param issueTime   发布期
     * @param issuer      发布人
     * @param image       图片地址
     * @param notes       备注
     * @param content     内容
     * @author shuyy
     * @date 2018/12/25 11:09
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String communityCode, String title, String introduce, String externalUrl, LocalDateTime validTime,
                     LocalDateTime issueTime, String issuer, String image, String notes, String content) {

        SelectionActivities selectionActivitie = new SelectionActivities(communityCode, title, introduce,
                externalUrl, validTime, issueTime,
                issuer, 0, image, notes, null);
        selectionActivitie.setGmtCreate(LocalDateTime.now());
        selectionActivitie.setGmtModified(LocalDateTime.now());
        selectionActivitiesMapper.insert(selectionActivitie);
        SelectionActivitiesContent selectionActivitiesContent = new SelectionActivitiesContent(selectionActivitie.getId(),
                content);
        selectionActivitiesContent.setGmtCreate(LocalDateTime.now());
        selectionActivitiesContent.setGmtModified(LocalDateTime.now());
        selectionActivitiesContentService.save(selectionActivitiesContent);
    }

    /**
     * 更新
     * @param id          id
     * @param title       标题
     * @param introduce   简介
     * @param externalUrl 外接地址
     * @param validTime   有效期
     * @param issueTime   发布期
     * @param issuer      发布人
     * @param image       图片地址
     * @param notes       备注
     * @param content     内容
     * @author shuyy
     * @date 2018/12/25 11:17
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String title, String introduce, String externalUrl, LocalDateTime validTime,
                       LocalDateTime issueTime, String issuer, String image, String notes, String content) {

        SelectionActivities selectionActivitie = new SelectionActivities();
        selectionActivitie.setId(id);
        if (StringUtils.isNotBlank(title)) {
            selectionActivitie.setTitle(title);
        }
        if (StringUtils.isNotBlank(introduce)) {
            selectionActivitie.setIntroduce(introduce);
        }
        if (StringUtils.isNotBlank(externalUrl)) {
            selectionActivitie.setExternalUrl(externalUrl);
        }
        if (validTime != null) {
            selectionActivitie.setValidTime(validTime);
        }
        if (issueTime != null) {
            selectionActivitie.setIssueTime(issueTime);
        }
        if (StringUtils.isNotBlank(issuer)) {
            selectionActivitie.setIssuer(issuer);
        }
        if (StringUtils.isNotBlank(image)) {
            selectionActivitie.setImage(image);
        }
        if (StringUtils.isNotBlank(notes)) {
            selectionActivitie.setNotes(notes);
        }
        selectionActivitie.setGmtModified(LocalDateTime.now());
        selectionActivitiesMapper.updateById(selectionActivitie);
        if (StringUtils.isNotBlank(content)) {
            SelectionActivitiesContent selectionActivitiesContent = selectionActivitiesContentService.getByselectionActivitiesId(id);
            selectionActivitiesContent.setContent(content);
            selectionActivitiesContentService.update(selectionActivitiesContent);
        }
    }

    /**
     * 删除
     * @param id
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/25 11:20
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        selectionActivitiesMapper.deleteById(id);
        selectionActivitiesContentService.removeByselectionActivitiesId(id);

    }

    /**
     * 分页查询
     * @param validTimeStart 过期开始时间
     * @param validTimeEnd   过期结束时间
     * @param issueTimeStart 发布开始时间
     * @param issueTimeEnd   发布结束时间
     * @param pageNum        当前页
     * @param pageSize       分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.SelectionActivities>
     * @author shuyy
     * @date 2018/12/25 11:44
     * @company mitesofor
     */
    public Page<SelectionActivities> listPage(String communityCode, LocalDateTime validTimeStart,
                                              LocalDateTime validTimeEnd,
                                              LocalDateTime issueTimeStart,
                                              LocalDateTime issueTimeEnd,
                                              Integer pageNum, Integer pageSize) {
        EntityWrapper<SelectionActivities> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (validTimeStart != null) {
            wrapper.ge("valid_time", validTimeStart);
        }
        if (validTimeEnd != null) {
            wrapper.le("valid_time", validTimeEnd);
        }
        if (issueTimeStart != null) {
            wrapper.ge("issue_time", issueTimeStart);
        }
        if (issueTimeEnd != null) {
            wrapper.le("issue_time", issueTimeEnd);
        }
        Page<SelectionActivities> page = new Page<>(pageNum, pageSize);
        List<SelectionActivities> selectionActivities = selectionActivitiesMapper.selectPage(page, wrapper);
        page.setRecords(selectionActivities);
        return page;
    }

}
