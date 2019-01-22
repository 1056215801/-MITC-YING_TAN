package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.Constants;
import com.mit.community.entity.*;
import com.mit.community.mapper.BusinessHandlingMapper;
import com.mit.community.util.MakeOrderNumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务办理业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 13:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BusinessHandlingService {
    @Autowired
    private BusinessHandlingMapper businessHandlingMapper;
    @Autowired
    private BusinessHandlingImgService businessHandlingImgService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private HouseholdRoomService householdRoomService;

    /**
     * 添加业务办理数据
     * @param businessHandling 业务办理数据
     * @return 添加成功条数
     * @author Mr.Deng
     * @date 13:59 2018/12/5
     */
    public Integer save(BusinessHandling businessHandling) {
        businessHandling.setGmtCreate(LocalDateTime.now());
        businessHandling.setGmtModified(LocalDateTime.now());
        return businessHandlingMapper.insert(businessHandling);
    }

    /**
     * 修改业务办理数据
     * @param businessHandling 业务办理数据
     * @return 修改成功数量
     * @author Mr.Deng
     * @date 14:52 2018/12/5
     */
    public Integer update(BusinessHandling businessHandling) {
        businessHandling.setGmtModified(LocalDateTime.now());
        return businessHandlingMapper.updateById(businessHandling);
    }

    /**
     * 查询业务办理信息，通过业务办理id
     * @param businessHandlingId 业务办理id
     * @return 业务办理信息
     * @author Mr.Deng
     * @date 14:53 2018/12/5
     */
    public BusinessHandling getById(Integer businessHandlingId) {
        return businessHandlingMapper.selectById(businessHandlingId);
    }

    /**
     * 申请业务办理
     * @param cellphone        手机号码
     * @param communityCode    小区code
     * @param roomId           房间id
     * @param roomNum          房间号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          申请内容
     * @param type             业务类型(关联字典表，code为business_handling_type。)
     * @param creatorUserId    创建人用户id
     * @param images           申请图片列表
     * @author Mr.Deng
     * @date 14:27 2018/12/5
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyBusinessHandling(String cellphone, String communityCode, Integer roomId, String roomNum,
                                      String contactPerson, String contactCellphone,
                                      String content, String type, Integer creatorUserId, List<String> images) {
        String order = "Y" + MakeOrderNumUtil.makeOrderNum();
        //报事成功code
        String status = "business_success";
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        if (houseHold != null) {
            Integer householdId = houseHold.getHouseholdId();
            HouseholdRoom householdRoom = householdRoomService.getByHouseholdIdAndRoomNum(householdId, roomNum);
            if (householdRoom != null) {
                BusinessHandling businessHandling = new BusinessHandling(order, communityCode, householdRoom.getCommunityName()
                        , householdRoom.getZoneId(), householdRoom.getZoneName(), householdRoom.getBuildingId(),
                        householdRoom.getBuildingName(), householdRoom.getUnitId(), householdRoom.getUnitName(),
                        roomId, roomNum, contactPerson, contactCellphone, content,
                        status, type, creatorUserId, 0, 0, 0,
                        0, StringUtils.EMPTY, StringUtils.EMPTY,
                        Constants.NULL_LOCAL_DATE_TIME, StringUtils.EMPTY, StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME,
                        Constants.NULL_LOCAL_DATE_TIME, null);
                this.save(businessHandling);
                if (!images.isEmpty()) {
                    for (String image : images) {
                        BusinessHandlingImg businessHandlingImg = new BusinessHandlingImg(businessHandling.getId(), image);
                        businessHandlingImgService.save(businessHandlingImg);
                    }
                }
            }
        }
    }

    /**
     * 查询业务办理详情信息，通过业务办理id
     * @param businessHandlingId 业务办理id
     * @return 业务办理
     * @author Mr.Deng
     * @date 19:36 2018/12/19
     */
    public BusinessHandling getByBusinessHandlingId(Integer businessHandlingId) {
        BusinessHandling businessHandling = this.getById(businessHandlingId);
        if (businessHandling != null) {
            List<BusinessHandlingImg> businessHandlingImgs = businessHandlingImgService.getByBusinessHandlingId(businessHandlingId);
            if (!businessHandlingImgs.isEmpty()) {
                List<String> images = businessHandlingImgs.stream().map(BusinessHandlingImg::getImgUrl).collect(Collectors.toList());
                businessHandling.setImages(images);
            }
        }
        return businessHandling;
    }

    /**
     * 查询业务办理状态数据，通过用户id
     * @param creatorUserId 用户id
     * @param status        业务办理状态 0、未完成。1、已完成
     * @return 业务办理数据列表
     * @author Mr.Deng
     * @date 14:40 2018/12/5
     */
    public Page<BusinessHandling> pageByStatus(Integer creatorUserId, Integer status,
                                               String communityCode,
                                               Integer pageNum, Integer pageSize) {
        Page<BusinessHandling> page = new Page<>(pageNum, pageSize);
        EntityWrapper<BusinessHandling> wrapper = new EntityWrapper<>();
        String[] s;
        //未完成
        if (status == 0) {
            s = new String[]{"business_success", "acceptance", "being_processed"};
        } else {
            s = new String[]{"remain_evaluated", "have_evaluation"};
        }
        wrapper.in("status", s);
        wrapper.eq("creator_user_id", creatorUserId);
        wrapper.eq("community_code", communityCode);
        wrapper.orderBy("gmt_create", false);
        List<BusinessHandling> businessHandlings = businessHandlingMapper.selectPage(page, wrapper);
        if (!businessHandlings.isEmpty()) {
            page.setRecords(businessHandlings);
        }
        return page;
    }

    /**
     * 受理
     * @param id           id
     * @param receiverName 受理人
     * @author shuyy
     * @date 2018/12/20 15:44
     * @company mitesofor
     */
    public void receive(Integer id, String receiverName) {
        BusinessHandling businessHandling = this.getById(id);
        businessHandling.setStatus("acceptance");
        businessHandling.setReceiverTime(LocalDateTime.now());
        businessHandling.setReceiver(receiverName);
        this.update(businessHandling);
    }

    /**
     * 处理
     * @param id             id
     * @param processor      处理人
     * @param processorPhone 处理人手机号
     * @author shuyy
     * @date 2018/12/20 10:55
     * @company mitesofor
     */
    public void processor(Integer id, String processor, String processorPhone) {
        BusinessHandling businessHandling = this.getById(id);
        businessHandling.setStatus("being_processed");
        businessHandling.setProcessorStartTime(LocalDateTime.now());
        businessHandling.setProcessor(processor);
        businessHandling.setProcessorPhone(processorPhone);
        this.update(businessHandling);
    }

    /**
     * 待评价
     * @param id id
     * @author shuyy
     * @date 2018/12/20 11:02
     * @company mitesofor
     */
    public void remailEvaluated(Integer id) {
        BusinessHandling businessHandling = this.getById(id);
        businessHandling.setStatus("remain_evaluated");
        businessHandling.setProcessorEndTime(LocalDateTime.now());
        this.update(businessHandling);
    }

    /**
     * 业务办理评价
     * @param businessHandlingId        业务办理id
     * @param evaluateResponseSpeed     响应速度评价
     * @param evaluateResponseAttitude  响应态度评价
     * @param evaluateTotal             总体评价
     * @param evaluateServiceProfession 服务专业度评价
     * @param evaluateContent           评价内容
     * @author Mr.Deng
     * @date 14:50 2018/12/5
     */
    @Transactional(rollbackFor = Exception.class)
    public void evaluateBusinessHandling(Integer businessHandlingId, Integer evaluateResponseSpeed,
                                         Integer evaluateResponseAttitude,
                                         Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        BusinessHandling businessHandling = this.getById(businessHandlingId);
        if (businessHandling != null) {
            businessHandling.setEvaluateContent(evaluateContent);
            businessHandling.setEvaluateResponseAttitude(evaluateResponseAttitude);
            businessHandling.setEvaluateResponseSpeed(evaluateResponseSpeed);
            businessHandling.setEvaluateServiceProfession(evaluateServiceProfession);
            businessHandling.setEvaluateTotal(evaluateTotal);
            businessHandling.setStatus("have_evaluation");
            this.update(businessHandling);

        }
    }

    /**
     * 查询业务办理列表
     * @param communityCode        小区code
     * @param zoneId               分区id
     * @param buildingId           楼栋id
     * @param unitId               单元id
     * @param roomId               房间id
     * @param cellphone            电话号码
     * @param status               状态
     * @param appointmentTimeStart 预约开始时间
     * @param appointmentTimeEnd   预约结束时间
     * @param type                 业务类型
     * @param pageNum              当前页
     * @param pageSize             分页大小
     * @return java.util.List<com.mit.community.entity.BusinessHandling>
     * @author shuyy
     * @date 2018/12/20 11:20
     * @company mitesofor
     */
    public Page<BusinessHandling> listPage(String communityCode, Integer zoneId, Integer buildingId, Integer unitId,
                                           Integer roomId, String cellphone, String status,
                                           String appointmentTimeStart, String appointmentTimeEnd, String type,
                                           Integer pageNum, Integer pageSize) {
        EntityWrapper<BusinessHandling> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (zoneId != null) {
            wrapper.eq("zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("unit_id", unitId);
        }
        if (roomId != null) {
            wrapper.eq("room_id", roomId);
        }
        if (StringUtils.isNotBlank(cellphone)) {
            wrapper.eq("cellphone", cellphone);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", status);
        }
        if (StringUtils.isNotBlank(appointmentTimeStart)) {
            wrapper.ge("appointment_time", appointmentTimeStart);
        }
        if (StringUtils.isNotBlank(appointmentTimeEnd)) {
            wrapper.le("appointment_time", appointmentTimeEnd);
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq("type", type);
        }
        wrapper.orderBy("gmt_create", false);
        Page<BusinessHandling> page = new Page<>(pageNum, pageSize);
        List<BusinessHandling> businessHandlings = businessHandlingMapper.selectPage(page, wrapper);
        page.setRecords(businessHandlings);
        return page;
    }

    /**
     * 查询业务办理总数，通过用户id和小区code
     * @param creatorUserId 用户id
     * @param communityCode 小区code
     * @return 统计总数
     * @author Mr.Deng
     * @date 13:40 2018/12/24
     */
    public Integer countByCreatorUserIdAndCommunityCode(Integer creatorUserId, String communityCode) {
        EntityWrapper<BusinessHandling> wrapper = new EntityWrapper<>();
        wrapper.eq("creator_user_id", creatorUserId);
        wrapper.eq("community_code", communityCode);
        return businessHandlingMapper.selectCount(wrapper);
    }

    /**
     * 查询业务办理总数，通过手机号和小区code
     * @param userId        用户id
     * @param communityCode 小区code
     * @return 统计总数
     * @author Mr.Deng
     * @date 13:49 2018/12/24
     */
    public Integer countByCellphoneAndCommunityCode(Integer userId, String communityCode) {
        return this.countByCreatorUserIdAndCommunityCode(userId, communityCode);
    }
}
