package com.mit.community.service;

import com.mit.community.entity.ExpressReadUser;
import com.mit.community.mapper.ExpressReadUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 快递提醒已读表业务处理层
 * @author Mr.Deng
 * @date 2018/12/14 16:52
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ExpressReadUserService {
    @Autowired
    private ExpressReadUserMapper expressReadUserMapper;

    /**
     * 添加快递信息已读记录
     * @param expressReadUser 快递信息已读记录
     * @author Mr.Deng
     * @date 16:53 2018/12/14
     */
    public void save(ExpressReadUser expressReadUser) {
        expressReadUserMapper.insert(expressReadUser);
    }
}
