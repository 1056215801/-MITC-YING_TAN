package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.Device;
import com.mit.community.entity.HouseHold;
import com.mit.community.mapper.AccessControlMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 门禁记录业务处理层
 * @author Mr.Deng
 * @date 2018/12/8 10:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService extends ServiceImpl<AccessControlMapper, AccessControl> {

    @Autowired
    private AccessControlMapper accessControlMapper;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private DeviceService deviceService;


}
