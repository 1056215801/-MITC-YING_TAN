package com.mit.community.module.communityservice;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.CommunityPhone;
import com.mit.community.entity.SysUser;
import com.mit.community.service.CommunityPhoneService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社区电话
 *
 * @author shuyy
 * @date 2018/12/21
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/communityPhone")
@Slf4j
@Api(tags = "社区电话")
public class CommunityPhoneController {
    @Autowired
    private CommunityPhoneService communityPhoneService;
    @Autowired
    private RedisService redisService;

    /**
     *
     * @param request request
     * @param communityCode 小区code
     * @param name 姓名
     * @param phone 电话
     * @param type 类型
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 20:18
     * @company mitesofor
    */
    @PostMapping("/save")
    @ApiOperation(value = "保存社区电话", notes = "传参：name 名， communityCode 小区code，" +
            " phone 电话，" +
            "type 类型 电话类型.关联字典code community_phone_type   社区电话类型1、物业电话；2、紧急电话，" +
            "")
    public Result save(HttpServletRequest request, String communityCode,
                       String name, String phone, String type) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        communityPhoneService.save(communityCode,
                name, phone, type, user.getId());
        return Result.success("保存成功");
    }


    /**
     *
     * @param request request
     * @param id id
     * @param name 姓名
     * @param phone 电话
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 20:18
     * @company mitesofor
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新社区电话", notes = "传参：name 名， communityCode 小区code，" +
            " phone 电话，" +
            "type 类型 电话类型.关联字典code community_phone_type   社区电话类型1、物业电话；2、紧急电话，" +
            "")
    public Result update(HttpServletRequest request, Integer id,
                       String name, String phone) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        communityPhoneService.update(id,
                name, phone, user.getId());
        return Result.success("更新成功");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 20:22
     * @company mitesofor
    */
    @DeleteMapping("/remove")
    @ApiOperation(value = "删除社区电话", notes = "传参：id")
    public Result remove( Integer id) {
        communityPhoneService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 20:23
     * @company mitesofor
    */
    @DeleteMapping("/list")
    @ApiOperation(value = "社区电话列表")
    public Result list( ) {
        List<CommunityPhone> list = communityPhoneService.list();
        return Result.success(list);
    }


}
