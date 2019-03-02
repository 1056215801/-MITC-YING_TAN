package com.mit.community.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.Constants;
import com.mit.community.entity.LostFound;
import com.mit.community.entity.LostFountContent;
import com.mit.community.entity.LostFountReadUser;
import com.mit.community.mapper.LostFoundMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 失物招领业务处理层
 * @author Mr.Deng
 * @date 2018/12/17 20:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class LostFoundService {
    @Autowired
    private LostFoundMapper lostFoundMapper;
    @Autowired
    private LostFountContentService lostFountContentService;
    @Autowired
    private LostFountReadUserService lostFountReadUserService;

    /**
     * 查询所有失物招领简介信息
     * @return 放回失物招领简介信息
     * @author Mr.Deng
     * @date 9:19 2018/12/18
     */
    public List<LostFound> list(String communityCode) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id,title,img_url as imgUrl,receiver_address as receiverAddress,receiver_status as receiverStatus");
        wrapper.orderBy("gmt_create", false);
        wrapper.eq("community_code", communityCode);
        return lostFoundMapper.selectList(wrapper);
    }

    /**
     * 查询所有失物招领简介信息
     * @return 放回失物招领简介信息
     * @author Mr.Deng
     * @date 9:19 2018/12/18
     */
    public Page<LostFound> listPage(Integer userId, String communityCode, Integer pageNum, Integer pageSize) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id,title,img_url as imgUrl,receiver_address as receiverAddress,receiver_status as receiverStatus");
        wrapper.orderBy("gmt_create", false);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("receiver_status", 1);
        Page<LostFound> page = new Page<>(pageNum, pageSize);
        List<LostFound> lostFounds = lostFoundMapper.selectPage(page, wrapper);
        if (!lostFounds.isEmpty()) {
            for (LostFound lostFound : lostFounds) {
                LostFountReadUser lostFountReadUsers = lostFountReadUserService.
                        getByUserIdByLostFountId(userId, lostFound.getId());
                if (lostFountReadUsers == null) {
                    lostFound.setReadStatus(false);
                } else {
                    lostFound.setReadStatus(true);
                }
            }
        }
        page.setRecords(lostFounds);
        return page;
    }

    /**
     * 查询失物招领信息 通过失物招领id
     * @param id 失物招领id
     * @return 失物招领信息
     * @author Mr.Deng
     * @date 9:32 2018/12/18
     */
    public LostFound getById(Integer id) {
        return lostFoundMapper.selectById(id);
    }

    /**
     * 查询失物招领详情信息，通过失物招领id
     * @param id 失物招领id
     * @return 失物招领详情信息
     * @author Mr.Deng
     * @date 9:44 2018/12/18
     */
    public LostFound getLostFountInfo(Integer id) {
        LostFound lostFound = this.getById(id);
        if (lostFound != null) {
            LostFountContent lostFountContent = lostFountContentService.listByLostFountId(id);
            String content = StringUtils.EMPTY;
            if (lostFountContent != null) {
                content = lostFountContent.getContent();
            }
            lostFound.setContent(content);
        }
        return lostFound;
    }

    /**
     * 保存
     * @param title         标题
     * @param imgUrl        图片地址
     * @param issuer        发布人
     * @param issuerPhone   发布电话
     * @param picAddress    捡到地址
     * @param pickTime      发布时间
     * @param communityCode 小区code
     * @param content       内容
     * @author shuyy
     * @date 2018/12/27 10:06
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String title, String imgUrl, String issuer, String issuerPhone,
                     String picAddress, String receiverAddress, LocalDateTime pickTime, String communityCode, String content
    ) {
        LostFound lostFound = new LostFound(title,
                imgUrl, issuer, issuerPhone, picAddress,
                pickTime, StringUtils.EMPTY, StringUtils.EMPTY,
                receiverAddress, Constants.NULL_LOCAL_DATE_TIME,
                1, communityCode, 0, null, null);
        lostFound.setGmtCreate(LocalDateTime.now());
        lostFound.setGmtModified(LocalDateTime.now());
        lostFoundMapper.insert(lostFound);
        LostFountContent lostFountContent = new LostFountContent(lostFound.getId(), content);
        lostFountContentService.save(lostFountContent);
    }

    /**
     * 更新
     * @param title           标题
     * @param imgUrl          图片地址
     * @param issuer          发布人
     * @param issuerPhone     发布电话
     * @param picAddress      捡到地址
     * @param pickTime        捡到时间
     * @param receiver        领取人
     * @param receivePhone    领取电话
     * @param receiverAddress 领取地址
     * @param receiverTime    领取时间
     * @param receiverStatus  领取状态
     * @param content         内容
     * @author shuyy
     * @date 2018/12/27 10:06
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String title, String imgUrl, String issuer, String issuerPhone,
                       String picAddress, LocalDateTime pickTime, String receiver, String receivePhone,
                       String receiverAddress, LocalDateTime receiverTime, Boolean receiverStatus, String content
    ) {

        LostFound lostFound = new LostFound();
        lostFound.setId(id);
        if (StringUtils.isNotBlank(title)) {
            lostFound.setTitle(title);
        }
        if (StringUtils.isNotBlank(imgUrl)) {
            lostFound.setImgUrl(imgUrl);
        }
        if (StringUtils.isNotBlank(issuer)) {
            lostFound.setIssuer(issuer);
        }
        if (StringUtils.isNotBlank(issuerPhone)) {
            lostFound.setIssuerPhone(issuerPhone);
        }
        if (StringUtils.isNotBlank(picAddress)) {
            lostFound.setPickAddress(picAddress);
        }
        if (pickTime != null) {
            lostFound.setPickTime(pickTime);
        }
        if (StringUtils.isNotBlank(receiver)) {
            lostFound.setReceiver(receiver);
        }
        if (StringUtils.isNotBlank(receivePhone)) {
            lostFound.setReceivePhone(receivePhone);
        }
        if (StringUtils.isNotBlank(receiverAddress)) {
            lostFound.setReceiverAddress(receiverAddress);
        }
        if (null != receiverTime) {
            lostFound.setReceiverTime(receiverTime);
        }
        if (receiverStatus != null) {
            lostFound.setReadStatus(receiverStatus);
        }
        lostFound.setGmtModified(LocalDateTime.now());
        lostFoundMapper.updateById(lostFound);
        if (StringUtils.isNotBlank(content)) {
            LostFountContent lostFountContent = new LostFountContent(lostFound.getId(), content);
            lostFountContentService.updateByLostFoudId(lostFountContent);
        }
    }

    /**
     * 删除
     * @param id id
     * @author shuyy
     * @date 2018/12/27 11:04
     * @company mitesofor
     */
    public void remove(Integer id) {
        lostFoundMapper.deleteById(id);
        lostFountContentService.removeByLostFoudId(id);
    }

    /**
     * 分页查询
     * @param communityCode     小区code
     * @param title             标题
     * @param issuer            发布人
     * @param issuerPhone       发布电话
     * @param receiver          领取人
     * @param receivePhone      领取人电话
     * @param receiverTimeStart 领取开始时间
     * @param receiverTimeEnd   领取结束时间
     * @param receiverStatus    领取状态
     * @param pageNum           当前页
     * @param pageSize          分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.LostFound>
     * @author shuyy
     * @date 2018/12/27 11:16
     * @company mitesofor
     */
    public Page<LostFound> listPage(String communityCode, String title, String issuer, String issuerPhone, String pickAddress, LocalDateTime pickTimeStart,
                                    LocalDateTime pickTimeEnd, String receiver, String receivePhone,
                                    LocalDateTime receiverTimeStart, LocalDateTime receiverTimeEnd,
                                    Integer receiverStatus, Integer pageNum, Integer pageSize) {
        Page<LostFound> page = new Page<>(pageNum, pageSize);
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(pickAddress)) {
            wrapper.like("pick_address", pickAddress);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.like("title", title, SqlLike.RIGHT);
        }
        if (StringUtils.isNotBlank(issuer)) {
            wrapper.like("issuer", issuer, SqlLike.RIGHT);
        }
        if (StringUtils.isNotBlank(issuerPhone)) {
            wrapper.like("issuer_phone", issuerPhone, SqlLike.RIGHT);
        }
        if (pickTimeStart != null) {
            wrapper.ge("pick_time", pickTimeStart);
        }
        if (pickTimeEnd != null) {
            wrapper.le("pick_time", pickTimeEnd);
        }
        if (StringUtils.isNotBlank(receiver)) {
            wrapper.like("receiver", receiver, SqlLike.RIGHT);
        }
        if (StringUtils.isNotBlank(receivePhone)) {
            wrapper.like("receive_phone", receivePhone, SqlLike.RIGHT);
        }
        if (receiverTimeStart != null) {
            wrapper.ge("receiver_time", receiverTimeStart);
        }
        if (receiverTimeEnd != null) {
            wrapper.le("receiver_time", receiverTimeEnd);
        }
        if (receiverStatus != null) {
            wrapper.eq("receiver_status", receiverStatus);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        wrapper.orderBy("pick_time", false);
        List<LostFound> lostFounds = lostFoundMapper.selectPage(page, wrapper);
        if (!lostFounds.isEmpty()) {
            lostFounds.stream().forEach(item -> {
                LostFountContent lostFountContent = lostFountContentService.listByLostFountId(item.getId());
                if (lostFountContent != null) {
                    item.setContent(lostFountContent.getContent());
                }
            });
        }
        page.setRecords(lostFounds);
        return page;
    }

    /**
     * 统计未读
     * @param communityCode 小区code
     * @return java.lang.Integer
     * @author shuyy
     * @date 2019-01-02 15:45
     * @company mitesofor
     */
    public Integer countNotRead(String communityCode, Integer userId) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);

        Integer num = lostFoundMapper.selectCount(wrapper);
        Integer readNum = lostFountReadUserService.countByUserId(userId);
        return num - readNum;
    }

    /**
     * 记录浏览量
     * @param lostFound 失物招领
     * @return 更新条数
     * @author Mr.Deng
     * @date 9:23 2019/1/4
     */
    public Integer updateReadNum(LostFound lostFound) {
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        Integer id = lostFound.getId();
        Integer readNum = lostFound.getReadNum();
        lostFound.setReadNum(readNum + 1);
        wrapper.eq("read_num", readNum).eq("id", id);
        return lostFoundMapper.update(lostFound, wrapper);
    }

    /**
     * 增加浏览量
     * @param lostFound 失物招领
     * @author Mr.Deng
     * @date 9:26 2019/1/4
     */
    public void addLostFoundReadNum(LostFound lostFound) {
        Integer num = this.updateReadNum(lostFound);
        if (num == 0) {
            lostFound = this.getById(lostFound.getId());
            if (lostFound == null) {
                return;
            } else {
                this.updateReadNum(lostFound);
            }
        }
        return;
    }

}
