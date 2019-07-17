package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.WarningConfig;
import com.mit.community.service.ClusterCommunityService;
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

@RequestMapping(value = "/warningConfig")
@RestController
@Slf4j
@Api(tags = "报警信息配置")
public class WarningConfigController {
    @Autowired
    private WarningConfigService warningConfigService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;

    @PostMapping("/list")
    @ApiOperation(value = "分页获取报警配置信息", notes = "Integer pageNum, Integer pageSize, String label:标签")
    public Result getListPage(HttpServletRequest request, Integer pageNum, Integer pageSize, String label, Integer isWarning){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        Page<WarningConfig> page = warningConfigService.getPage(pageNum, pageSize, label, communityCode, isWarning);
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

    @PostMapping("/update")
    @ApiOperation(value = "更新是否报警信息", notes = "")
    public Result update(HttpServletRequest request, Integer id,int isWarning, String warningInfo){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        warningConfigService.update(id, isWarning, warningInfo);
        return Result.success("更新成功");
    }

    /*@PostMapping("/updateWarningInfo")
    @ApiOperation(value = "更新报警信息", notes = "")
    public Result update(HttpServletRequest request, Integer id,String warningInfo){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        warningConfigService.updateWarningInfo(id, warningInfo);
        return Result.success("更新成功");
    }*/


}
