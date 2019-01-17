package com.mit.community.module.communityservice;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.CommunityServiceInfo;
import com.mit.community.entity.SysUser;
import com.mit.community.service.CommunityServiceInfoService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 小区服务
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/communityServiceInfo")
@Slf4j
@Api(tags = "小区服务")
public class CommunityServiceInfoController {

    @Autowired
    private CommunityServiceInfoService communityServiceInfoService;
    @Autowired
    private RedisService redisService;

    /**
     * @param name          名
     * @param intro         简介
     * @param businessHours 营业时间
     * @param address       地址
     * @param cellphone     电话
     * @param longitude     经度
     * @param latitude      维度
     * @param image         图片
     * @param type          类型
     * @param detail        详情
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 16:57
     * @company mitesofor
     * @author shuyy
     * @date 2018/12/20 17:05
     * @company mitesofor
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存小区服务", notes = "传参：name 名， intro 简介， businessHours 营业时间，" +
            "address 地址， cellphone 号码， longitude 经度，latitude 维度，  image 图片， type 社区服务类型.关联字典code community_service_type 社区服务类型 1、社区门诊2、开锁换锁3、送水到家， detail 详情")
    public Result save(HttpServletRequest request, String name, String intro, String businessHours, String address, String cellphone,
                       Double longitude, Double latitude, MultipartFile image, String type, String detail) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String imageUrl = UploadUtil.upload(image);
        communityServiceInfoService.save(user.getCommunityCode(), name, intro, businessHours, address, cellphone, longitude, latitude, imageUrl, type, user.getId(), detail);
        return Result.success("保存成功");
    }

    /**
     * @param request       request
     * @param id            id
     * @param name          名
     * @param intro         简介
     * @param businessHours 营业时间
     * @param address       地址
     * @param cellphone     电话
     * @param longitude     经度
     * @param latitude      维度
     * @param image         图片
     * @param detail        详情
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 17:21
     * @company mitesofor
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改小区服务", notes = "传参：id 社区服务id， name 名， intro 简介， businessHours 营业时间，" +
            "address 地址， cellphone 号码， longitude 经度，latitude 维度，  image 图片， detail 详情")
    public Result update(HttpServletRequest request, Integer id, String name, String intro, String businessHours, String address, String cellphone,
                         Double longitude, Double latitude, MultipartFile image, String detail) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String imageUrl = null;
        if (image != null) {
            imageUrl = UploadUtil.upload(image);
        }
        communityServiceInfoService.update(id,
                name, intro, businessHours, address, cellphone, longitude, latitude, imageUrl,
                user.getId(), detail);
        return Result.success("修改成功");
    }

    /**
     * 分页查询
     * @param request       request
     * @param communityCode 小区code
     * @param type          类型
     * @param pageNum       pageNum
     * @param pageSize      pageSize
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/20 17:28
     * @company mitesofor
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询小区服务", notes = "传参：communityCode 小区code，不传则查当前小区， type 类型")
    public Result listPage(HttpServletRequest request, String communityCode, String type, Integer pageNum, Integer pageSize) {
        if (communityCode == null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = user.getCommunityCode();
        }
        Page<CommunityServiceInfo> communityServiceInfoPage = communityServiceInfoService.listPage(communityCode, type, pageNum, pageSize);
        return Result.success(communityServiceInfoPage);
    }

    /**
     * 获取社区服务
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 10:55
     * @company mitesofor
    */
    @GetMapping("/getById")
    @ApiOperation(value = "获取社区服务", notes = "传参：id 社区服务id")
    public Result getById(Integer id) {
        if (id == null) {
            Result.error("参数错误");
        }
        CommunityServiceInfo communityServiceInfo = communityServiceInfoService.getDetailById(id);
        return Result.success(communityServiceInfo);
    }

    /**
     * @param id 社区服务id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/21 15:29
     * @company mitesofor
    */
    @DeleteMapping("/removeById")
    @ApiOperation(value = "删除社区服务", notes = "传参：id 社区服务id")
    public Result removeById(Integer id) {
        if (id == null) {
            Result.error("参数错误");
        }
        communityServiceInfoService.remove(id);
        return Result.success("操作成功");
    }





}