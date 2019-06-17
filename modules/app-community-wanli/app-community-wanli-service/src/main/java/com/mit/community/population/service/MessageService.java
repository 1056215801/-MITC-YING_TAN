package com.mit.community.population.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.User;
import com.mit.community.entity.entity.Message;
import com.mit.community.entity.entity.MessageAccept;
import com.mit.community.mapper.mapper.MessageAcceptMapper;
import com.mit.community.mapper.mapper.MessageMapper;
import com.mit.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageAcceptMapper messageAcceptMapper;

    public Page<Message> listPage(LocalDateTime timeStart, LocalDateTime timeEnd,
                                  String content, Integer pageNum, Integer pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        EntityWrapper<Message> wrapper = new EntityWrapper<>();
        if (timeStart != null) {
            wrapper.ge("gmt_create", timeStart);
        }
        if (timeEnd != null) {
            wrapper.le("gmt_create", timeEnd);
        }
        if (StringUtils.isNotBlank(content)) {
            wrapper.like("content",content, SqlLike.DEFAULT);
        }
        wrapper.orderBy("gmt_create", false);
        List<Message> list = messageMapper.selectPage(page, wrapper);
        if (!list.isEmpty()) {
            User user = null;
            for (int i=0;i<list.size();i++) {
                user = userService.getById(list.get(i).getUserId());
                EntityWrapper<MessageAccept> messageAcceptWrapper = new EntityWrapper<>();
                messageAcceptWrapper.eq("messageId", list.get(i).getId());
                int count = messageAcceptMapper.selectCount(messageAcceptWrapper);
                list.get(i).setUserName(user.getName());
                list.get(i).setCount(count);
            }
        }
        page.setRecords(list);
        return page;
    }


}
