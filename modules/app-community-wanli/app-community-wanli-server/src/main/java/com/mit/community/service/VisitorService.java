package com.mit.community.service;

import com.mit.community.entity.Visitor;
import com.mit.community.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客业务处理层
 * @author Mr.Deng
 * @date 2018/12/3 16:46
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 查询所有访客信息
     * @return 访客信息列表
     * @author Mr.Deng
     * @date 17:16 2018/12/3
     */
    public List<Visitor> list() {
        return visitorMapper.selectList(null);
    }

}
