package com.mit.community.service;

import com.mit.community.entity.VisitorMessage;
import com.mit.community.mapper.VisitorMessageMapper;
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
    private VisitorMessageMapper visitorMessageMapper;

    /**
     * 保存
     * @param mobile
     * @param title
     * @author shuyy
     * @date 2019-01-02 17:19
     * @company mitesofor
    */
    public void save(String mobile, String title){
        VisitorMessage visitorMessage = new VisitorMessage(mobile, title);
        visitorMessageMapper.insert(visitorMessage);
    }

}
