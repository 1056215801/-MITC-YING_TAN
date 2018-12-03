package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ReportThingRepairImg;
import com.mit.community.entity.ReportThingsRepair;
import com.mit.community.mapper.ReportThingsRepairMapper;
import com.mit.community.util.MakeOrderNumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报事报修业务层
 * @author Mr.Deng
 * @date 2018/12/3 19:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ReportThingsRepairService {
    @Autowired
    private ReportThingsRepairMapper reportThingsRepairMapper;

    @Autowired
    private ReportThingRepairImgService reportThingRepairImgService;

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
     * 查询报事报修状态数据，通过住户id
     * @param householdId 住户id
     * @param status      保修状态 0、未完成。1、已完成
     * @return 报事报修数据列表
     * @author Mr.Deng
     * @date 20:49 2018/12/3
     */
    public List<ReportThingsRepair> listByStatus(Integer householdId, Integer status) {
        EntityWrapper<ReportThingsRepair> wrapper = new EntityWrapper<>();
        //未完成
        if (status == 0) {
            wrapper.ge("status", 1);
            wrapper.le("status", 3);
        }
        //已完成
        if (status == 1) {
            wrapper.ge("status", 4);
        }
        wrapper.eq("household_id", householdId);
        return reportThingsRepairMapper.selectList(wrapper);
    }

    /**
     * 申请报事报修
     * @param communityCode 小区code
     * @param communityName 小区名
     * @param zoneId        分区id
     * @param zoneName      分区名
     * @param buildingId    楼栋id
     * @param buildingName  楼栋名
     * @param unitId        单元id
     * @param unitName      单元名
     * @param roomId        房间id
     * @param roomNum       房间号
     * @param householdId   用户id
     * @param content       报事内容
     * @param reportUser    报事人
     * @param cellphone     联系人
     * @param maintainType  维修类型
     * @param creatorUserId 创建用户id
     * @author Mr.Deng
     * @date 20:02 2018/12/3
     */
    public void applyReportThingsRepair(String communityCode, String communityName, Integer zoneId, String zoneName,
                                        Integer buildingId, String buildingName, Integer unitId, String unitName,
                                        Integer roomId, String roomNum, Integer householdId, String content,
                                        String reportUser, String cellphone, Integer maintainType, Integer creatorUserId,
                                        List<String> images) {
        String number = MakeOrderNumUtil.makeOrderNum();
        ReportThingsRepair reportThingsRepair = new ReportThingsRepair(number, communityCode, communityName, zoneId,
                zoneName, buildingId, buildingName, unitId, unitName, roomId, roomNum, householdId, content, 1,
                reportUser, cellphone, LocalDateTime.now(), 0, 0,
                0, 0, StringUtils.EMPTY, maintainType, creatorUserId);
        Integer reportThingsRepairSize = this.save(reportThingsRepair);
        //报事信息是否添加成功
        if (reportThingsRepairSize > 0) {
            if (images.size() > 0 && StringUtils.isNotBlank(images.get(0))) {
                for (String image : images) {
                    ReportThingRepairImg reportThingRepairImg = new ReportThingRepairImg(reportThingsRepair.getId(), image);
                    reportThingRepairImgService.save(reportThingRepairImg);
                }
            }
        }
    }

}
