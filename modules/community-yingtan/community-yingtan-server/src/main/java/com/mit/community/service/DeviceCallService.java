package com.mit.community.service;

import com.mit.community.entity.DeviceCall;
import com.mit.community.mapper.DeviceCallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 呼叫记录业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 15:56
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class DeviceCallService {
    @Autowired
    private DeviceCallMapper deviceCallMapper;

    /**
     * 添加呼叫记录信息
     *
     * @param deviceCall 呼叫记录信息
     * @author Mr.Deng
     * @date 15:57 2018/11/15
     */
    public void save(DeviceCall deviceCall) {
        deviceCallMapper.insert(deviceCall);
    }

    /**
     * 获取所有的呼叫记录信息
     *
     * @return 呼叫记录列表
     * @author Mr.Deng
     * @date 15:59 2018/11/15
     */
    public List<DeviceCall> list() {
        return deviceCallMapper.selectList(null);
    }
}
