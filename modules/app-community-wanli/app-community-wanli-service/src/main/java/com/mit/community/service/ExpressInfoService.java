package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ExpressInfo;
import com.mit.community.mapper.ExpressInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 快递信息业务处理层
 * @author Mr.Deng
 * @date 2018/12/14 16:54
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressInfoService {
    @Autowired
    private ExpressInfoMapper expressInfoMapper;

    /**
     * 添加快递信息
     * @param expressInfo 快递信息
     * @author Mr.Deng
     * @date 16:30 2018/12/27
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(ExpressInfo expressInfo) {
        expressInfo.setGmtCreate(LocalDateTime.now());
        expressInfo.setGmtModified(LocalDateTime.now());
        expressInfoMapper.insert(expressInfo);
    }

    /**
     * 修改快递信息数据
     * @author Mr.Deng
     * @date 16:55 2018/12/14
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, Integer userId, String waybillNum, Integer expressAddressId, String createUserName, String receiver,
                       String receiverPhone) {
        ExpressInfo expressInfo = new ExpressInfo();
        expressInfo.setId(id);
        if (userId != null) {
            expressInfo.setUserId(userId);
        }
        if (StringUtils.isNotBlank(waybillNum)) {
            expressInfo.setWaybillNum(waybillNum);
        }
        if (expressAddressId != null) {
            expressInfo.setExpressAddressId(expressAddressId);
        }
        if (StringUtils.isNotBlank(createUserName)) {
            expressInfo.setCreateUserName(createUserName);
        }
        //快递已领取
        if (StringUtils.isNotBlank(receiver)) {
            expressInfo.setReceiver(receiver);
            expressInfo.setReceiveTime(LocalDateTime.now());
            expressInfo.setReceiveStatus(1);
            if (StringUtils.isNotBlank(receiverPhone)) {
                expressInfo.setReceiverPhone(receiverPhone);
            }
        }
        expressInfo.setGmtModified(LocalDateTime.now());
        expressInfoMapper.updateById(expressInfo);
    }

    /**
     * 删除快递信息，根据快递信息id
     * @param id 快递信息id
     * @author Mr.Deng
     * @date 9:23 2018/12/28
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        expressInfoMapper.deleteById(id);
    }

    /**
     * 查询快递未领取个数，通过用户id和快递位置信息
     * @param userId           用户id
     * @param expressAddressId 垮堤位置信息
     * @return 快递个数
     * @author Mr.Deng
     * @date 17:08 2018/12/14
     */
    public Integer countNotExpressNum(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        wrapper.eq("receive_status", 2);
        return expressInfoMapper.selectCount(wrapper);
    }

    /**
     * 查询快递总个数，通过用户id和快递位置信息
     * @param userId           用户id
     * @param expressAddressId 垮堤位置信息
     * @return 快递个数
     * @author Mr.Deng
     * @date 17:08 2018/12/14
     */
    public Integer countExpressNum(Integer userId, Integer expressAddressId) {
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        return expressInfoMapper.selectCount(wrapper);
    }

    /**
     * 查询快递详细信息
     * @param userId           用户id
     * @param expressAddressId 快递地址信息
     * @return 快递信息
     * @author Mr.Deng
     * @date 17:58 2018/12/17
     */
    public Page<ExpressInfo> listExpressInfoPage(Integer userId, Integer expressAddressId, Integer pageNum, Integer pageSize) {
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        Page<ExpressInfo> page = new Page<>(pageNum, pageSize);
        wrapper.eq("user_id", userId);
        wrapper.eq("express_address_id", expressAddressId);
        wrapper.orderBy("gmt_create", false);
        List<ExpressInfo> expressInfos = expressInfoMapper.selectPage(page, wrapper);
        page.setRecords(expressInfos);
        return page;
    }

    /**
     * 分页查询
     * @param communityCode    小区code
     * @param userId           app用户id
     * @param expressAddressId 快递领取位置id
     * @param waybillNum       运单号
     * @param receiveStatus    领取状态1、已领取2、未领取
     * @param receiveTimeStart 领取开始时间
     * @param receiveTimeEnd   领取结束时间
     * @param receiver         领取人
     * @param receiverPhone    领取人手机号
     * @param createUserName   添加人
     * @param pageNum          页数
     * @param pageSize         一页数量
     * @return 分页数据
     * @author Mr.Deng
     * @date 9:50 2018/12/28
     */
    public Page<ExpressInfo> listPage(String communityCode, Integer userId, Integer expressAddressId, String waybillNum,
                                      Integer receiveStatus, LocalDateTime receiveTimeStart,
                                      LocalDateTime receiveTimeEnd, String receiver, String receiverPhone,
                                      String createUserName, Integer pageNum, Integer pageSize) {
        Page<ExpressInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<ExpressInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (expressAddressId != null) {
            wrapper.eq("express_address_id", expressAddressId);
        }
        if (StringUtils.isNotBlank(waybillNum)) {
            wrapper.eq("waybill_num", waybillNum);
        }
        if (receiveStatus != null) {
            wrapper.eq("receive_status", receiveStatus);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        if (receiveTimeStart != null) {
            wrapper.ge("receive_time", receiveTimeStart);
        }
        if (receiveTimeEnd != null) {
            wrapper.le("receive_time", receiveTimeEnd);
        }
        if (StringUtils.isNotBlank(receiver)) {
            wrapper.eq("receiver", receiver);
        }
        if (StringUtils.isNotBlank(receiverPhone)) {
            wrapper.eq("receiver_phone", receiverPhone);
        }
        if (StringUtils.isNotBlank(createUserName)) {
            wrapper.eq("create_user_name", createUserName);
        }
        wrapper.orderBy("gmt_create", false);
        List<ExpressInfo> expressInfos = expressInfoMapper.selectPage(page, wrapper);
        page.setRecords(expressInfos);
        return page;
    }

}
