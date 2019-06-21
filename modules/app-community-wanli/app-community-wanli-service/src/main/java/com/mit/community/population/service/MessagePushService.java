package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.User;
import com.mit.community.entity.entity.Message;
import com.mit.community.entity.entity.MessageAccept;
import com.mit.community.entity.entity.MessageCheck;
import com.mit.community.entity.entity.MessageUser;
import com.mit.community.mapper.MessagePushMapper;
import com.mit.community.mapper.mapper.MessageAcceptMapper;
import com.mit.community.mapper.mapper.MessageMapper;
import com.mit.community.service.UserService;
import com.mit.community.util.WebPush;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MessagePushService {
    @Autowired
    private MessagePushMapper messagePushMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageAcceptMapper messageAcceptMapper;

    public void pushMessage(Integer ageStart, Integer ageEnd, String sex, String edu, String job, String marriage, String politics,
                            String rycf, String rysx, String title, String outline, String content, Integer userId) {
        EntityWrapper<MessageUser> wrapper = new EntityWrapper<>();
        if (ageStart != 0) {
            wrapper.ge("b.age", ageStart);
        }
        if (ageEnd != 0) {
            wrapper.le("b.age", ageEnd);
        }
        if (StringUtils.isNotBlank(sex)) {
            wrapper.in("b.gender", sex);
        }
        if (StringUtils.isNotBlank(edu)) {
            wrapper.in("b.education", edu);
        }
        if (StringUtils.isNotBlank(job)) {
            wrapper.in("b.job_type", job);
        }
        if (StringUtils.isNotBlank(marriage)) {
            wrapper.in("b.matrimony", marriage);
        }
        if (StringUtils.isNotBlank(politics)) {
            wrapper.in("b.politic_countenance", politics);
        }
        if (StringUtils.isNotBlank(rycf)) {
            if ("流动人口".equals(rycf)) {
                wrapper.in("b.rksx", "2");
            } else {
                wrapper.in("b.rksx", "1");
            }
        }
        List<MessageUser> list = messagePushMapper.getMessageUser(wrapper);
        List<String> target = new ArrayList<>();
        if (!list.isEmpty()) {
            if (StringUtils.isNotBlank(rysx)) {
                String[] labels = rysx.split(",");
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    MessageUser messageUser = (MessageUser) iter.next();
                    for (int i = 0; i < labels.length; i++) {
                        if (messageUser.getLabel().contains(labels[i])) {
                            target.add(messageUser.getUserId().toString());
                            break;
                        }
                    }
                }
            } else {
                for (MessageUser messageUser : list) {
                    target.add(messageUser.getUserId().toString());
                }
            }
            if (!target.isEmpty()) {
                Message message = new Message(title, outline, content, userId, null, 0);
                message.setGmtCreate(LocalDateTime.now());
                message.setGmtModified(LocalDateTime.now());
                messageMapper.insert(message);
                //List<MessageAccept> messageAcceptList = new ArrayList<>();
                MessageAccept messageAccept = null;
                for (int i = 0; i < target.size(); i++) {
                    messageAccept = new MessageAccept();
                    messageAccept.setMessageId(message.getId());
                    messageAccept.setUserId(Integer.valueOf(target.get(i)));
                    messageAccept.setStatus(1);
                    messageAccept.setGmtCreate(LocalDateTime.now());
                    //messageAccept.setGmtModified(LocalDateTime.now());
                    //messageAcceptList.add(messageAccept);
                    messageAcceptMapper.insert(messageAccept);
                }
                //WebPush.sendAlias(outline, target);//需要标题
                WebPush.sendAllsetNotification(outline, title);
            }
        }
    }

    public void pushUser(String title, String outline, String content,Integer userId, String idNum) {
        List<String> target = new ArrayList<>();
        List<String> noUser = new ArrayList<>();
        String[] idNums = idNum.split(",");
        User user = null;
        for (int i=0;i<idNums.length;i++) {
            user = userService.getByIDNumber(idNums[i]);
            if (user != null) {
                target.add(user.getId().toString());
            } else {
                noUser.add(idNums[i]);
            }
        }

        if (!target.isEmpty()) {
            Message message = new Message(title, outline, content, userId, null, 0);
            message.setGmtCreate(LocalDateTime.now());
            message.setGmtModified(LocalDateTime.now());
            messageMapper.insert(message);
                //List<MessageAccept> messageAcceptList = new ArrayList<>();
            MessageAccept messageAccept = null;
            for (int i = 0; i < target.size(); i++) {
                messageAccept = new MessageAccept();
                messageAccept.setMessageId(message.getId());
                messageAccept.setUserId(Integer.valueOf(target.get(i)));
                messageAccept.setStatus(1);
                messageAccept.setGmtCreate(LocalDateTime.now());
                //messageAccept.setGmtModified(LocalDateTime.now());
                    //messageAcceptList.add(messageAccept);
                messageAcceptMapper.insert(messageAccept);
            }
                WebPush.sendAlias(title, outline, target);//需要标题
                //WebPush.sendAllsetNotification(outline, title);
        }
    }

    public Page<MessageCheck> messageAcceptListPage(Integer messageId, String name, Integer status, Integer pageNum, Integer pageSize) {
        Page<MessageCheck> page = new Page<>(pageNum, pageSize);
        EntityWrapper<MessageCheck> wrapper = new EntityWrapper<>();
        if (messageId != null) {
            wrapper.eq("a.messageId", messageId);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("c.name", name);
        }
        if (status != 0) {
            wrapper.eq("a.status", status);
        }
        List<MessageCheck> list = messagePushMapper.messageAcceptListPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public List<Message> getMessage(Integer userId){
        //List<Message> readed = messagePushMapper
        List<Message> readed = new ArrayList<>();
        return readed;
    }
}
