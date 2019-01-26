package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.mapper.ReportThingsRepairMapper;
import com.mit.community.util.MakeOrderNumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报事报修业务层
 * @author Mr.Deng
 * @date 2018/12/3 19:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ReportThingsRepairService extends ServiceImpl<ReportThingsRepairMapper, ReportThingsRepair> {
    @Autowired
    private ReportThingsRepairMapper reportThingsRepairMapper;
    @Autowired
    private ReportThingRepairImgService reportThingRepairImgService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private HouseholdRoomService householdRoomService;
    @Autowired
    private RedisService redisService;

    /**
     * 添加保修报修数据
     * @param reportThingsRepair 报事报修数据
     * @return 添加条数
     * @author Mr.Deng
     * @date 19:47 2018/12/3
     */
    public Integer save(ReportThingsRepair reportThingsRepair) {
        reportThingsRepair.setGmtCreate(LocalDateTime.now());
        reportThingsRepair.setGmtModified(LocalDateTime.now());
        return reportThingsRepairMapper.insert(reportThingsRepair);
    }

    /**
     * 查询报事报修信息，通过报事报修id
     * @param id 报事报修id
     * @return 报事报修信息
     * @author Mr.Deng
     * @date 11:00 2018/12/5
     */
    public ReportThingsRepair getById(Integer id) {
        return reportThingsRepairMapper.selectById(id);
    }

    /**
     * 更新报事报修数据
     * @param reportThingsRepair 更新的数据
     * @return 更新条数
     * @author Mr.Deng
     * @date 11:03 2018/12/5
     */
    public Integer update(ReportThingsRepair reportThingsRepair) {
        reportThingsRepair.setGmtCreate(LocalDateTime.now());
        return reportThingsRepairMapper.updateById(reportThingsRepair);
    }

    /**
     * 受理
     * @param id           报事报修id
     * @param receiverName 受理人
     * @author shuyy
     * @date 2018/12/20 10:42
     * @company mitesofor
     */
    public void receive(Integer id, String receiverName) {
        ReportThingsRepair reportThingsRepair = this.getById(id);
        reportThingsRepair.setStatus("acceptance");
        reportThingsRepair.setReceiverTime(LocalDateTime.now());
        reportThingsRepair.setReceiver(receiverName);
        this.update(reportThingsRepair);
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
        ReportThingsRepair reportThingsRepair = this.getById(id);
        reportThingsRepair.setStatus("being_processed");
        reportThingsRepair.setProcessorStartTime(LocalDateTime.now());
        reportThingsRepair.setProcessor(processor);
        reportThingsRepair.setProcessorPhone(processorPhone);
        this.update(reportThingsRepair);
    }

    /**
     * 待评价
     * @param id
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/20 11:02
     * @company mitesofor
     */
    public void remailEvaluated(Integer id) {
        ReportThingsRepair reportThingsRepair = this.getById(id);
        reportThingsRepair.setStatus("remain_evaluated");
        reportThingsRepair.setProcessorEndTime(LocalDateTime.now());
        this.update(reportThingsRepair);
    }

    /**
     * 查询报事报修状态数据，通过住户id
     * @param householdId 住户id
     * @param status      保修状态 0、未完成。1、已完成
     * @return 报事报修数据列表
     * @author Mr.Deng
     * @date 20:49 2018/12/3
     */
    public Page<ReportThingsRepair> pageByStatus(Integer householdId, Integer status, Integer pageNum, Integer pageSize) {
        Page<ReportThingsRepair> page = new Page<>(pageNum, pageSize);
        EntityWrapper<ReportThingsRepair> wrapper = new EntityWrapper<>();
        String[] s;
        //未完成
        if (status == 0) {
            s = new String[]{"business_success", "acceptance", "being_processed"};
        } else {
            s = new String[]{"remain_evaluated", "have_evaluation"};
        }
        wrapper.in("status", s);
        wrapper.eq("household_id", householdId);
        wrapper.orderBy("gmt_create", false);
        List<ReportThingsRepair> reportThingsRepairList = reportThingsRepairMapper.selectPage(page, wrapper);
        if (!reportThingsRepairList.isEmpty()) {
            page.setRecords(reportThingsRepairList);
        }
        return page;
    }

    /**
     * 查询报事报修列表
     * @param communityCode        小区code
     * @param zoneId               分区id
     * @param buildingId           楼栋id
     * @param unitId               单元id
     * @param roomId               房间id
     * @param cellphone            电话号码
     * @param status               状态
     * @param appointmentTimeStart 预约开始时间
     * @param appointmentTimeEnd   预约结束时间
     * @param maintainType         维修类型
     * @param pageNum              当前页
     * @param pageSize             分页大小
     * @return java.util.List<com.mit.community.entity.ReportThingsRepair>
     * @author shuyy
     * @date 2018/12/20 11:20
     * @company mitesofor
     */
    public Page<ReportThingsRepair> listPage(String communityCode, Integer zoneId, Integer buildingId, Integer unitId,
                                             Integer roomId, String cellphone, String status,
                                             String appointmentTimeStart, String appointmentTimeEnd, String gmtCreateStart,
                                             String gmtCreateEnd, String maintainType,
                                             Integer pageNum, Integer pageSize) {
        EntityWrapper<ReportThingsRepair> wrapper = new EntityWrapper<>();
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
        if (StringUtils.isNotBlank(gmtCreateStart)) {
            wrapper.ge("gmt_create", gmtCreateStart);
        }
        if (StringUtils.isNotBlank(gmtCreateEnd)) {
            wrapper.le("gmt_create", gmtCreateEnd);
        }
        if (StringUtils.isNotBlank(maintainType)) {
            wrapper.eq("maintain_type", maintainType);
        }
        wrapper.orderBy("appointment_time", false);
        Page<ReportThingsRepair> page = new Page<>(pageNum, pageSize);
        List<ReportThingsRepair> reportThingsRepairList = reportThingsRepairMapper.selectPage(page, wrapper);
        if (!reportThingsRepairList.isEmpty()) {
            List<Integer> reportThingsRepairIds = reportThingsRepairList.stream().map(ReportThingsRepair::getId).collect(Collectors.toList());
            List<ReportThingRepairImg> reportThingRepairImgs = reportThingRepairImgService.getByReportThingsRepairIds(reportThingsRepairIds);
            reportThingsRepairList.forEach(item -> {
                List<String> imglist = Lists.newArrayListWithExpectedSize(5);
                reportThingRepairImgs.forEach(itemImg -> {
                    if (item.getId().equals(itemImg.getReportThingRepairId())) {
                        imglist.add(itemImg.getImgUrl());
                    }
                });
                item.setImages(imglist);
            });
        }
        page.setRecords(reportThingsRepairList);
        return page;
    }

    /**
     * 查询报事报修状态数据，通过手机号
     * @param cellphone 手机号
     * @param status    保修状态 0、未完成。1、已完成
     * @return List<ReportThingsRepair>
     * @author Mr.Deng
     * @date 17:01 2018/12/11
     */
    public Page<ReportThingsRepair> listReportThingsRepairByStatus(String cellphone, Integer status, Integer pageNum, Integer pageSize) {
        Page<ReportThingsRepair> page = new Page<>(pageNum, pageSize);
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        return pageByStatus(user.getHouseholdId(), status, pageNum, pageSize);
    }

    /**
     * 申请报事报修
     * @param communityCode   小区code
     * @param roomId          房间id
     * @param roomNum         房间号
     * @param content         报事内容
     * @param reportUser      报事人
     * @param reportCellphone 联系人电话
     * @param maintainType    维修类型.关联字典code maintain_type 维修类型：1、水，2、电，3、可燃气，4、锁，5、其他
     * @param creatorUserId   创建用户id
     * @author Mr.Deng
     * @date 20:02 2018/12/3
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyReportThingsRepair(String communityCode, String cellphone, Integer roomId, String roomNum, String content,
                                        String reportUser, String reportCellphone, String maintainType, Integer creatorUserId,
                                        LocalDateTime appointmentTime, List<String> images) {
        String number = "B" + MakeOrderNumUtil.makeOrderNum();
        //报事成功code
        String status = "business_success";
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        if (houseHold != null) {
            Integer householdId = houseHold.getHouseholdId();
            HouseholdRoom householdRoom = householdRoomService.getByHouseholdIdAndRoomNum(householdId, roomNum);
            if (householdRoom != null) {
                ReportThingsRepair reportThingsRepair = new ReportThingsRepair(number, communityCode, householdRoom.getCommunityName(),
                        householdRoom.getZoneId(), householdRoom.getZoneName(), householdRoom.getBuildingId(), householdRoom.getBuildingName(),
                        householdRoom.getUnitId(), householdRoom.getUnitName(), roomId, roomNum, householdId,
                        content, status, reportUser, reportCellphone, appointmentTime, 0, 0,
                        0, 0, StringUtils.EMPTY, maintainType, creatorUserId, StringUtils.EMPTY,
                        Constants.NULL_LOCAL_DATE_TIME, StringUtils.EMPTY, StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME,
                        Constants.NULL_LOCAL_DATE_TIME, null);
                this.save(reportThingsRepair);
                if (!images.isEmpty()) {
                    for (String image : images) {
                        ReportThingRepairImg reportThingRepairImg = new ReportThingRepairImg(reportThingsRepair.getId(), image);
                        reportThingRepairImgService.save(reportThingRepairImg);
                    }
                }
            }
        }
    }

    /**
     * 查询报事报修详情，通告报事报修id
     * @param reportThingsRepairId 报事报修id
     * @return 报事报修详情信息
     * @author Mr.Deng
     * @date 19:00 2018/12/19
     */
    public ReportThingsRepair getReportThingsRepair(Integer reportThingsRepairId) {
        ReportThingsRepair reportThingsRepair = this.getById(reportThingsRepairId);
        if (reportThingsRepair != null) {
            List<ReportThingRepairImg> reportThingRepairImgs = reportThingRepairImgService.getByReportThingsRepairId(reportThingsRepairId);
            if (!reportThingRepairImgs.isEmpty()) {
                List<String> images = reportThingRepairImgs.stream().map(ReportThingRepairImg::getImgUrl).collect(Collectors.toList());
                reportThingsRepair.setImages(images);
            }
        }
        return reportThingsRepair;
    }

    /**
     * 报事报修评价
     * @param applyReportId             报事报修id
     * @param evaluateResponseSpeed     响应速度评价
     * @param evaluateResponseAttitude  响应态度评价
     * @param evaluateTotal             总体评价
     * @param evaluateServiceProfession 服务专业度评价
     * @param evaluateContent           评价内容
     * @author Mr.Deng
     * @date 10:57 2018/12/5
     */
    @Transactional(rollbackFor = Exception.class)
    public void evaluateReportThingsRepair(Integer applyReportId, Integer evaluateResponseSpeed, Integer evaluateResponseAttitude,
                                           Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        String status = "have_evaluation";
        ReportThingsRepair reportThingsRepair = this.getById(applyReportId);
        reportThingsRepair.setEvaluateContent(evaluateContent);
        reportThingsRepair.setEvaluateResponseAttitude(evaluateResponseAttitude);
        reportThingsRepair.setEvaluateResponseSpeed(evaluateResponseSpeed);
        reportThingsRepair.setEvaluateServiceProfession(evaluateServiceProfession);
        reportThingsRepair.setEvaluateTotal(evaluateTotal);
        reportThingsRepair.setStatus(status);
        this.update(reportThingsRepair);
    }

    /**
     * 统计报事报修数量,通过住户id和小区code
     * @param householdId   住户id
     * @param communityCode 小区code
     * @return 总数
     * @author Mr.Deng
     * @date 11:40 2018/12/24
     */
    public Integer countByhouseholdIdAndCommunityCode(Integer householdId, String communityCode) {
        EntityWrapper<ReportThingsRepair> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("community_code", communityCode);
        return reportThingsRepairMapper.selectCount(wrapper);
    }

    /**
     * 统计报事报修数量，通过手机号和小区code
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return 总数
     * @author Mr.Deng
     * @date 11:35 2018/12/24
     */
    public Integer countReportThingsRepair(String cellphone, String communityCode) {
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        Integer reportThingsRepairNum = 0;
        if (houseHold != null) {
            reportThingsRepairNum = this.countByhouseholdIdAndCommunityCode(houseHold.getHouseholdId(), communityCode);
        }
        return reportThingsRepairNum;
    }

}
