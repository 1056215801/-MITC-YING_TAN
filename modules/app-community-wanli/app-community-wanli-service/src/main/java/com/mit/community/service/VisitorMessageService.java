package com.mit.community.service;

import com.mit.community.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 访客消息service
 *
 * @author shuyy
 * @date 2019-01-02
 * @company mitesofor
 */
@Service
public class VisitorMessageService {

    @Autowired
    private VisitorMapper visitorMapper;


}
