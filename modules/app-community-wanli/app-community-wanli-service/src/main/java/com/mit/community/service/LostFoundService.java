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
        LostFountContent lostFountContent = lostFountContentService.listByLostFountId(id);
        String content = StringUtils.EMPTY;
        if (lostFountContent != null) {
            content = lostFountContent.getContent();
        }
        lostFound.setContent(content);
        Integer readNum = lostFountReadUserService.countByLostFountId(id);
        lostFound.setReadNum(readNum);
        return lostFound;
    }

    /**
     * 查询所有的失物招领信息
     * @param userId 用户id
     * @return 失物招领信息
     * @author Mr.Deng
     * @date 10:02 2018/12/18
     */
    public List<LostFound> listAll(Integer userId, String communityCode) {
        List<LostFound> list = this.list(communityCode);
        if (!list.isEmpty()) {
            for (LostFound lostFound : list) {
                List<LostFountReadUser> lostFountReadUsers = lostFountReadUserService.getByUserIdByLostFountId(userId, lostFound.getId());
                if (lostFountReadUsers.isEmpty()) {
                    lostFound.setReadStatus(false);
                } else {
                    lostFound.setReadStatus(true);
                }
            }
        }
        return list;
    }

    /**
     * 保存
     * @param title 标题
     * @param imgUrl 图片地址
     * @param issuer 发布人
     * @param issuerPhone 发布电话
     * @param picAddress 捡到地址
     * @param issueTime 发布时间
     * @param communityCode 小区code
     * @param content 内容
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/27 10:06
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void save(String title, String imgUrl, String issuer, String issuerPhone,
                     String picAddress, LocalDateTime issueTime, String communityCode, String content
                     ){
        LostFound lostFound = new LostFound(title,
                imgUrl, issuer, issuerPhone, picAddress,
                issueTime, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME,
                1, communityCode, null, null, null);
        LostFountContent lostFountContent = new LostFountContent(lostFound.getId(), content);
        lostFound.setGmtCreate(LocalDateTime.now());
        lostFound.setGmtModified(LocalDateTime.now());
        lostFountContentService.save(lostFountContent);
        lostFoundMapper.insert(lostFound);
    }

    /**
     * 更新
     * @param title 标题
     * @param imgUrl 图片地址
     * @param issuer 发布人
     * @param issuerPhone 发布电话
     * @param picAddress 捡到地址
     * @param issueTime 发布时间
     * @param content 内容
     * @author shuyy
     * @date 2018/12/27 10:06
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, String title, String imgUrl, String issuer, String issuerPhone,
                     String picAddress, LocalDateTime issueTime, String content
    ){

        LostFound lostFound = new LostFound();
        lostFound.setId(id);
        if(StringUtils.isNotBlank(title)){
            lostFound.setTitle(title);
        }
        if(StringUtils.isNotBlank(imgUrl)){
            lostFound.setImgUrl(imgUrl);
        }
        if(StringUtils.isNotBlank(issuer)){
            lostFound.setIssuer(issuer);
        }
        if(StringUtils.isNotBlank(issuerPhone)){
            lostFound.setIssuerPhone(issuerPhone);
        }
        if(StringUtils.isNotBlank(picAddress)){
            lostFound.setPickAddress(picAddress);
        }
        if(issueTime != null){
            lostFound.setIssueTime(issueTime);
        }
        lostFound.setGmtModified(LocalDateTime.now());
        lostFoundMapper.updateById(lostFound);
        if(StringUtils.isNotBlank(content)){
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
    public void remove(Integer id){
        lostFoundMapper.deleteById(id);
        lostFountContentService.removeByLostFoudId(id);
    }

    /**
     * 分页查询
     * @param communityCode 小区code
     * @param title 标题
     * @param issuer 发布人
     * @param issuerPhone 发布电话
     * @param issueTimeStart 发布开始时间
     * @param issueTimeEnd 发布结束时间
     * @param receiver 领取人
     * @param receivePhone 领取人电话
     * @param receiverTimeStart 领取开始时间
     * @param receiverTimeEnd 领取结束时间
     * @param receiverStatus 领取状态
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.LostFound>
     * @author shuyy
     * @date 2018/12/27 11:16
     * @company mitesofor
    */
    public Page<LostFound> listPage(String communityCode, String title, String issuer, String issuerPhone, LocalDateTime issueTimeStart,
                                    LocalDateTime issueTimeEnd, String receiver, String receivePhone,
                                    LocalDateTime receiverTimeStart,LocalDateTime receiverTimeEnd,
                                    Integer receiverStatus, Integer pageNum, Integer pageSize){
        Page<LostFound> page = new Page<>(pageNum, pageSize);
        EntityWrapper<LostFound> wrapper = new EntityWrapper<>();
        if(StringUtils.isNotBlank(title)){
            wrapper.like("title", title, SqlLike.RIGHT);
        }
        if(StringUtils.isNotBlank(issuer)){
            wrapper.like("issuer", issuer, SqlLike.RIGHT);
        }
        if(StringUtils.isNotBlank(issuerPhone)){
            wrapper.like("issuer_phone", issuerPhone, SqlLike.RIGHT);
        }
        if(issueTimeStart != null){
            wrapper.ge("issue_time", issueTimeStart);
        }
        if(issueTimeEnd != null){
            wrapper.le("issue_time", issueTimeEnd);
        }
        if(StringUtils.isNotBlank(receiver)){
            wrapper.like("receiver", receiver, SqlLike.RIGHT);
        }
        if(StringUtils.isNotBlank(receivePhone)){
            wrapper.like("receive_phone", receivePhone, SqlLike.RIGHT);
        }
        if(receiverTimeStart != null){
            wrapper.ge("receiver_time", receiverTimeStart);
        }
        if(receiverTimeEnd != null){
            wrapper.le("receiver_time", receiverTimeEnd);
        }
        if(receiverStatus != null){
            wrapper.eq("receiver_status", receiverStatus);
        }
        if(StringUtils.isNotBlank(communityCode)){
            wrapper.eq("community_code", communityCode);
        }
        wrapper.orderBy("issue_time", false);
        List<LostFound> lostFounds = lostFoundMapper.selectPage(page, wrapper);
        page.setRecords(lostFounds);
        return page;

    }

}
