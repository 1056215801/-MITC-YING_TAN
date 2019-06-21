package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.MessageAccept;
import com.mit.community.mapper.MessageManageMapper;
import com.mit.community.mapper.MessagePushMapper;
import com.mit.community.mapper.mapper.MessageAcceptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageManageService {
    @Autowired
    private MessageManageMapper messageManageMapper;

    @Autowired
    private MessageAcceptMapper messageAcceptMapper;

    @Autowired
    private MessagePushMapper messagePushMapper;

    /*@Transactional
    public void aa(){
        EntityWrapper<MessageAccept> wrapper = new EntityWrapper<>();
        wrapper.eq("messageId", messageId);
        wrapper.eq("userId", userId);
        MessageAccept messageAccept = new MessageAccept();
        messageAccept.setStatus(2);
        messageAcceptMapper.update(messageAccept, wrapper);
    }*/

    public int getMessageNoReadCount(Integer userId){
        int count = messagePushMapper.getMessageCountByStatus(userId, 1);
        return count;
    }



}
