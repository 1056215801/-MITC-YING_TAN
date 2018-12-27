package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

}
