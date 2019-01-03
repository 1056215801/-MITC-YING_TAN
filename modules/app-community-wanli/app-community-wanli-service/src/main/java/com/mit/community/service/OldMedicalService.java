package com.mit.community.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.OldMedical;
import com.mit.community.entity.OldMedicalContent;
import com.mit.community.entity.OldMedicalReadUser;
import com.mit.community.mapper.OldMedicalMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 老人体检业务处理层
 * @author Mr.Deng
 * @date 2018/12/18 19:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class OldMedicalService {
    @Autowired
    private OldMedicalMapper oldMedicalMapper;
    @Autowired
    private OldMedicalContentService oldMedicalContentService;
    @Autowired
    private OldMedicalReadUserService oldMedicalReadUserService;

    /**
     * 添加老人体检信息
     * @param communityCode 小区code
     * @param title         标题
     * @param issuer        发布人姓名
     * @param contacts      联系人
     * @param phone         联系人手机号
     * @param address       登记地址
     * @param startTime     活动开始时间
     * @param endTime       活动结束时间
     * @param content       活动详情
     * @author Mr.Deng
     * @date 17:03 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String communityCode, String title, String issuer, String contacts, String phone, String address,
                     LocalDateTime startTime, LocalDateTime endTime, String content) {
        OldMedical oldMedical = new OldMedical(communityCode, title, issuer, LocalDateTime.now(), contacts, phone,
                address, startTime, endTime, null, null, null, null);
        oldMedical.setGmtCreate(LocalDateTime.now());
        oldMedical.setGmtModified(LocalDateTime.now());
        oldMedicalMapper.insert(oldMedical);
        OldMedicalContent oldMedicalContent = new OldMedicalContent(oldMedical.getId(), content);
        oldMedicalContentService.save(oldMedicalContent);
    }

    /**
     * 更新数据
     * @param id        老人体检id
     * @param title     标题
     * @param contacts  联系人
     * @param phone     联系人手机号
     * @param address   地址
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param content   详情
     * @author Mr.Deng
     * @date 17:22 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String title, String contacts, String phone, String address, LocalDateTime startTime, LocalDateTime endTime, String content) {
        OldMedical oldMedical = new OldMedical();
        oldMedical.setId(id);
        oldMedical.setGmtModified(LocalDateTime.now());
        if (StringUtils.isNotBlank(title)) {
            oldMedical.setTitle(title);
        }
        if (StringUtils.isNotBlank(contacts)) {
            oldMedical.setContacts(contacts);
        }
        if (StringUtils.isNotBlank(phone)) {
            oldMedical.setPhone(phone);
        }
        if (StringUtils.isNotBlank(address)) {
            oldMedical.setAddress(address);
        }
        if (null != startTime) {
            oldMedical.setStartTime(startTime);
        }
        if (null != endTime) {
            oldMedical.setEndTime(endTime);
        }
        oldMedicalMapper.updateById(oldMedical);
        if (StringUtils.isNotBlank(content)) {
            OldMedicalContent oldMedicalContent = new OldMedicalContent(oldMedical.getId(), content);
            oldMedicalContentService.updateByOldMedicalId(oldMedicalContent);
        }
    }

    /**
     * 分页
     * @param communityCode   小区code
     * @param title           标题
     * @param issuer          发布人
     * @param issuerTimeStart 发布时间开始
     * @param issuerTimeEnd   发布时间结束
     * @param contacts        联系人
     * @param phone           联系人手机号
     * @param address         登记地址
     * @param startTime       活动开始时间开始
     * @param startTimeLast   活动开始时间结束
     * @param endTime         活动结束时间开始
     * @param endTimeLast     活动结束时间结束
     * @param pageNum         页数
     * @param pageSize        一页显示数量
     * @return page<oldMedical>
     * @author Mr.Deng
     * @date 9:06 2019/1/3
     */
    public Page<OldMedical> listPage(String communityCode, String title, String issuer, LocalDateTime issuerTimeStart,
                                     LocalDateTime issuerTimeEnd,
                                     String contacts, String phone, String address, LocalDateTime startTime, LocalDateTime startTimeLast,
                                     LocalDateTime endTime, LocalDateTime endTimeLast, Integer pageNum, Integer pageSize) {
        EntityWrapper<OldMedical> wrapper = new EntityWrapper<>();
        Page<OldMedical> page = new Page<>(pageNum, pageSize);
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.like("title", title);
        }
        if (StringUtils.isNotBlank(issuer)) {
            wrapper.like("issuer", issuer, SqlLike.RIGHT);
        }
        if (null != issuerTimeStart) {
            wrapper.ge("issuer_time", issuerTimeStart);
        }
        if (null != issuerTimeEnd) {
            wrapper.le("issuer_time", issuerTimeEnd);
        }
        if (StringUtils.isNotBlank(contacts)) {
            wrapper.like("contacts", contacts);
        }
        if (StringUtils.isNotBlank(phone)) {
            wrapper.eq("phone", phone);
        }
        if (StringUtils.isNotBlank(address)) {
            wrapper.like("address", address);
        }
        if (null != startTime) {
            wrapper.ge("start_time", startTime);
        }
        if (null != startTimeLast) {
            wrapper.le("start_time", startTimeLast);
        }
        if (null != endTime) {
            wrapper.ge("end_time", endTime);
        }
        if (null != endTimeLast) {
            wrapper.le("end_time", endTimeLast);
        }
        List<OldMedical> oldMedicals = oldMedicalMapper.selectPage(page, wrapper);
        page.setRecords(oldMedicals);
        return page;
    }

    /**
     * 删除老人体检
     * @param id 老人体检id
     * @author Mr.Deng
     * @date 17:36 2019/1/2
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        oldMedicalMapper.deleteById(id);
        oldMedicalContentService.remove(id);
    }

    /**
     * 查询老人体检信息，通过小区code
     * @param communityCode 小区code
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 19:58 2018/12/18
     */
    public Page<OldMedical> listPageByCommunityCode(Integer userId, String communityCode, Integer pageNum, Integer pageSize) {
        EntityWrapper<OldMedical> wrapper = new EntityWrapper<>();
        Page<OldMedical> page = new Page<>(pageNum, pageSize);
        wrapper.eq("community_code", communityCode);
        wrapper.orderBy("gmt_create", false);
        List<OldMedical> oldMedicals = oldMedicalMapper.selectPage(page, wrapper);
        if (!oldMedicals.isEmpty()) {
            for (OldMedical oldMedical : oldMedicals) {
                String status = getStatus(oldMedical.getStartTime(), oldMedical.getEndTime());
                oldMedical.setOldMedicalStatus(status);
                List<OldMedicalReadUser> oldMedicalReadUsers = oldMedicalReadUserService.getByUserIdOldMedicalId(userId, oldMedical.getId());
                if (oldMedicalReadUsers.isEmpty()) {
                    oldMedical.setReadStatus(false);
                } else {
                    oldMedical.setReadStatus(true);
                }
            }
        }
        page.setRecords(oldMedicals);
        return page;
    }

    /**
     * 查询老人体检信息，通过老人体检id
     * @param id 老人体检id
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 20:00 2018/12/18
     */
    public OldMedical getById(Integer id) {
        return oldMedicalMapper.selectById(id);
    }

    /**
     * 查询老人体检信息，通过老人体检id
     * @param oldMedicalId 老人体检id
     * @return 老人体检信息
     * @author Mr.Deng
     * @date 20:12 2018/12/18
     */
    public OldMedical getByOldMedicalId(Integer oldMedicalId) {
        OldMedical oldMedical = this.getById(oldMedicalId);
        if (oldMedical != null) {
            Integer readNum = oldMedicalReadUserService.countByUserIdOldMedicalId(oldMedicalId);
            oldMedical.setReadNum(readNum);
            String status = getStatus(oldMedical.getStartTime(), oldMedical.getEndTime());
            oldMedical.setOldMedicalStatus(status);
            OldMedicalContent oldMedicalContent = oldMedicalContentService.getByOldMedicalId(oldMedicalId);
            if (oldMedicalContent != null) {
                oldMedical.setContent(oldMedicalContent.getContent());
            }
        }
        return oldMedical;
    }

    /**
     * 判断当前活动
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     * @return 活动状态
     * @author Mr.Deng
     * @date 17:09 2018/12/18
     */
    private static String getStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime nowTime = LocalDateTime.now();
        if (nowTime.isBefore(startTime)) {
            return "未开始";
        }
        if (nowTime.isAfter(endTime)) {
            return "已结束";
        }
        return "进行中";
    }

    /**
     * 统计未读
     * @param communityCode 小区code
     * @param userId        userId
     * @return java.lang.Integer
     * @author shuyy
     * @date 2019-01-02 16:11
     * @company mitesofor
     */
    public Integer countNotRead(String communityCode, Integer userId) {
        EntityWrapper<OldMedical> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        Integer num = oldMedicalMapper.selectCount(wrapper);
        Integer notRead = oldMedicalReadUserService.countReadNum(userId);
        return num - notRead;

    }

}
