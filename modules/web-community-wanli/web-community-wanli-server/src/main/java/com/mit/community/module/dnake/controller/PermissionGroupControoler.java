package com.mit.community.module.dnake.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 改写狄耐克权限组模块
 *
 * @author xq
 * @date 2019/7/15
 * @company mitesofor
 */

@RestController
@RequestMapping("/permissionGroupControoler")
@Slf4j
@Api(tags = "狄耐克替代接口")
public class PermissionGroupControoler {

}
