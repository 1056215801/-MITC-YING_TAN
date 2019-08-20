package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.PersonLabel;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.WarningConfig;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.PersonLabelService;
import com.mit.community.service.RedisService;
import com.mit.community.service.WarningConfigService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 标签管理
 *
 * @author xiong
 * @date 2019/7/15
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: mitesofor </p>
 */
@RequestMapping(value = "/personLabel")
@RestController
@Slf4j
@Api(tags = "人员标签信息管理")
public class PersonLabelController {
    @Autowired
    private PersonLabelService personLabelService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WarningConfigService warningConfigService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;

    @PostMapping("/list")
    @ApiOperation(value = "分页获取人员标签信息", notes = "Integer pageNum, Integer pageSize, String label:标签")
    public Result getListPage(HttpServletRequest request, Integer pageNum, Integer pageSize, String label){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        Integer isWarning = null;
        Page<WarningConfig> page = warningConfigService.getPage(pageNum, pageSize, label, communityCode,isWarning);
        List<WarningConfig> list = page.getRecords();
        if (!list.isEmpty()) {
            ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
            for(int i=0; i<list.size(); i++){
                list.get(i).setCommunityCode(clusterCommunity.getCommunityName());
            }
            page.setRecords(list);
        }
        return Result.success(page);
    }

    @PostMapping("/listAll")
    @ApiOperation(value = "获取全部标签信息", notes = "")
    public Result getListPage(HttpServletRequest request){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        List<WarningConfig> list = warningConfigService.getList(communityCode);
        return Result.success(list);
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新人员标签信息", notes = "参数：id 记录id， labelName 标签名称， remarks 备注")
    public Result update(HttpServletRequest request, Integer id,String labelName,String remarks){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        warningConfigService.update(id, labelName, remarks, communityCode);
        return Result.success("更新成功");
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存人员标签信息", notes = "")
    public Result save(HttpServletRequest request, String labelName, String remarks){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        WarningConfig warningConfig = warningConfigService.getByLabelAndCommunityCode(labelName, communityCode);
        if (warningConfig != null){
            return Result.error("已经存在同名标签，请勿重复添加");
        } else {
            warningConfigService.save(labelName, remarks, communityCode);
            return Result.success("保存成功");
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "保存人员标签信息", notes = "")
    public Result delete(HttpServletRequest request, Integer id){
        warningConfigService.delete(id);
        return Result.success("删除成功");
    }

}
