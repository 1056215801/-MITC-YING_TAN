package com.mit.community.module.userservice.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.BusinessHandling;
import com.mit.community.entity.SysUser;
import com.mit.community.service.BusinessHandlingService;
import com.mit.community.service.RedisService;
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
 * 业务办理
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@RequestMapping(value = "/bussinessHandleController")
@RestController
@Slf4j
@Api(tags = "业务办理")
public class BussinessHandleController {

    @Autowired
    private BusinessHandlingService businessHandlingService;
    @Autowired
    private RedisService redisService;

    /**
     *
     * @param request request
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 16:01
     * @company mitesofor
    */
    @PatchMapping("/receive")
    @ApiOperation(value = "受理业务办理", notes = "输入参数：id 业务办理id")
    public Result receive(HttpServletRequest request, Integer id) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        businessHandlingService.receive(id, user.getName());
        return Result.success("受理成功");
    }

    /**
     * @param id id
     * @param processor 处理人
     * @param processorPhone 处理人手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 10:59
     * @company mitesofor
     */
    @PatchMapping("/processor")
    @ApiOperation(value = "处理业务办理", notes = "输入参数：id 业务办理id," +
            "processor 处理人， processorPhone 处理人手机号")
    public Result processor(Integer id,
                                                String processor, String processorPhone) {
        businessHandlingService.processor(id,
                processor, processorPhone);
        return Result.success("操作成功");
    }

    /**
     * @param id 业务办理id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 11:03
     * @company mitesofor
     */
    @PatchMapping("/remailEvaluated")
    @ApiOperation(value = "待评价业务办理", notes = "输入参数：id 业务办理id")
    public Result remailEvaluated(Integer id) {
        businessHandlingService.remailEvaluated(id);
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
     * @param type 业务类型
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 11:25
     * @company mitesofor
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询业务办理列表", notes = "输入参数：zoneId 分区id，buildingId 楼栋id，unitId 单元id，" +
            "roomId 房间id，cellphone 联系号码， status 状态。关联书字典，code为，report_thing_repair_type。1、申请成功。2、已受理。3、处理中。4、待评价。5、已评价。" +
            "  appointmentTimeStart 预约开始时间、appointmentTimeEnd 预约结束时间、" +
            " maintainType 维修类型。关联字典code type 业务类型，关联字典表，code为business_handling_type。1、入住证明。2、装修完工申请。3、大物件搬出申报。4、装修许可证。5、装修出入证。6、钥匙托管。7、业主卡。99、其他。 ")
    public Result listPage(HttpServletRequest request, Integer zoneId, Integer buildingId, Integer unitId,
                           Integer roomId, String cellphone, String status,
                           String appointmentTimeStart, String appointmentTimeEnd, String type,
                           Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        List<BusinessHandling> businessHandlings = businessHandlingService.listPage(communityCode,
                zoneId, buildingId, unitId, roomId,
                cellphone, status, appointmentTimeStart, appointmentTimeEnd, type, pageNum, pageSize);
        return Result.success(businessHandlings);
    }
}
