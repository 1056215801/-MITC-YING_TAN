package com.mit.community.module.userservice.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ReportThingsRepair;
import com.mit.community.entity.SysUser;
import com.mit.community.service.RedisService;
import com.mit.community.service.ReportThingsRepairService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 报事报修
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@RequestMapping(value = "/reportThingRepairController")
@RestController
@Slf4j
@Api(tags = "报事报修")
public class ReportThingRepairController {

    @Autowired
    private ReportThingsRepairService reportThingsRepairService;
    @Autowired
    private RedisService redisService;

    /**
     * @param request              rquest
     * @param reportThingsRepairId 报事报修id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 10:46
     * @company mitesofor
     */
    @PatchMapping("/receiveReportThingsRepairId")
    @ApiOperation(value = "受理报事报修", notes = "输入参数：reportThingsRepairId 报事报修id")
    public Result receiveReportThingsRepairId(HttpServletRequest request, Integer reportThingsRepairId) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        reportThingsRepairService.receive(reportThingsRepairId, user.getName());
        return Result.success("受理成功");
    }

    /**
     * @param reportThingsRepairId 报事报修id
     * @param processor 处理人
     * @param processorPhone 处理人手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 10:59
     * @company mitesofor
    */
    @PatchMapping("/processorReportThingsRepairId")
    @ApiOperation(value = "处理报事报修", notes = "输入参数：reportThingsRepairId 报事报修id," +
            "processor 处理人， processorPhone 处理人手机号")
    public Result processorReportThingsRepairId(Integer reportThingsRepairId,
                          String processor, String processorPhone) {
        reportThingsRepairService.processor(reportThingsRepairId,
                processor, processorPhone);
        return Result.success("操作成功");
    }

    /**
     * @param reportThingsRepairId 报事报修id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 11:03
     * @company mitesofor
    */
    @PatchMapping("/remailEvaluatedReportThingsRepairId")
    @ApiOperation(value = "待评价报事报修", notes = "输入参数：reportThingsRepairId 报事报修id")
    public Result remailEvaluatedReportThingsRepairId(Integer reportThingsRepairId) {
        reportThingsRepairService.remailEvaluated(reportThingsRepairId);
        return Result.success("操作成功");
    }

    /**
     *
     * @param request
     * @param zoneId 分区id
     * @param buildingId 楼栋id
     * @param unitId 单元id
     * @param roomId 房间id
     * @param cellphone 联系号码
     * @param status 状态
     * @param appointmentTimeStart 预约开始时间
     * @param appointmentTimeEnd 预约结束时间
     * @param maintainType 维修类型
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 11:25
     * @company mitesofor
    */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询报事报修列表", notes = "输入参数：zoneId 分区id，buildingId 楼栋id，unitId 单元id，" +
            "roomId 房间id，cellphone 联系号码， status 状态。关联书字典，code为，report_thing_repair_type。1、报事成功。2、已受理。3、处理中。4、待评价。5、已评价。" +
            "  appointmentTimeStart 预约开始时间、appointmentTimeEnd 预约结束时间、" +
            " maintainType 维修类型。关联字典code maintain_type 维修类型：1、水，2、电，3、可燃气，4、锁，5、其他 ")
    public Result listPage(HttpServletRequest request, Integer zoneId, Integer buildingId, Integer unitId,
                                                      Integer roomId, String cellphone, String status,
                                                      String appointmentTimeStart, String appointmentTimeEnd, String maintainType,
                                                      Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        List<ReportThingsRepair> reportThingsRepairs = reportThingsRepairService.listPage(communityCode,
                zoneId, buildingId, unitId, roomId,
                cellphone, status, appointmentTimeStart, appointmentTimeEnd, maintainType, pageNum, pageSize);
        return Result.success(reportThingsRepairs);
    }


}
