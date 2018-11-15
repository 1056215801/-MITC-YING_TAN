package com.mit.community.service;

import com.mit.community.entity.AccessControl;
import com.mit.community.mapper.AccessControlMapper;
import com.sun.jmx.snmp.internal.SnmpAccessControlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 门禁记录业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 11:55
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService {
    @Autowired
    private AccessControlMapper accessControlMapper;

    /**
     * 添加门禁记录
     *
     * @param accessControl 门禁记录信息
     * @return
     * @author Mr.Deng
     * @date 11:57 2018/11/15
     */
    public void save(AccessControl accessControl) {
        accessControlMapper.insert(accessControl);
    }

}
