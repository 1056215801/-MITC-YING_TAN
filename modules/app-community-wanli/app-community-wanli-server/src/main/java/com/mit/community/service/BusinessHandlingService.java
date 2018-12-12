package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.*;
import com.mit.community.mapper.BusinessHandlingMapper;
import com.mit.community.module.system.service.DictionaryService;
import com.mit.community.util.MakeOrderNumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private DictionaryService dictionaryService;
    @Autowired
    private UserTrackService userTrackService;
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
                        0, StringUtils.EMPTY);

                this.save(businessHandling);
                if (!images.isEmpty()) {
                    for (String image : images) {
                        BusinessHandlingImg businessHandlingImg = new BusinessHandlingImg(businessHandling.getId(), image);
                        businessHandlingImgService.save(businessHandlingImg);
                    }
                }
            }
        }
        //记录足迹
        Dictionary dictionary = dictionaryService.getByCode(status);
        Dictionary byCode = dictionaryService.getByCode(type);
        if (dictionary != null && byCode != null) {
            String nameType = byCode.getName();
            String nameStatus = dictionary.getName();
            userTrackService.addUserTrack(cellphone, nameType, nameType + nameStatus);
        }
    }

    /**
     * 查询业务办理状态数据，通过用户id
     * @param creatorUserId 用户id
     * @param status        业务办理状态 0、未完成。1、已完成
     * @return 业务办理数据列表
     * @author Mr.Deng
     * @date 14:40 2018/12/5
     */
    public List<BusinessHandling> listByStatus(Integer creatorUserId, Integer status) {
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
        return businessHandlingMapper.selectList(wrapper);
    }

    /**
     * 业务办理评价
     * @param cellphone                 手机号
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
    public void evaluateBusinessHandling(String cellphone, Integer businessHandlingId, Integer evaluateResponseSpeed,
                                         Integer evaluateResponseAttitude,
                                         Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        BusinessHandling businessHandling = this.getById(businessHandlingId);
        if (businessHandling != null) {
            businessHandling.setEvaluateContent(evaluateContent);
            businessHandling.setEvaluateResponseAttitude(evaluateResponseAttitude);
            businessHandling.setEvaluateResponseSpeed(evaluateResponseSpeed);
            businessHandling.setEvaluateServiceProfession(evaluateServiceProfession);
            businessHandling.setEvaluateTotal(evaluateTotal);
            this.update(businessHandling);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(businessHandling.getType());
            if (dictionary != null) {
                String name = dictionary.getName();
                userTrackService.addUserTrack(cellphone, name, businessHandling.getContactPerson() + "-" + name + "评价成功");
            }
        }
    }

}
