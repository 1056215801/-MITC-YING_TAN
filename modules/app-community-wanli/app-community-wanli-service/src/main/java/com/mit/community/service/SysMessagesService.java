package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.SysMessages;
import com.mit.community.entity.User;
import com.mit.community.mapper.SysMessagesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统消息业务处理层
 * @author Mr.Deng
 * @date 2018/12/19 10:31
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
@Slf4j
public class SysMessagesService {
    @Autowired
    private SysMessagesMapper sysMessagesMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SysMessageReadService sysMessageReadService;

    /**
     * 添加系统消息
     * @param sysMessages 系统消息
     * @author Mr.Deng
     * @date 10:41 2018/12/19
     */
    public void save(SysMessages sysMessages) {
        sysMessages.setGmtCreate(LocalDateTime.now());
        sysMessages.setGmtModified(LocalDateTime.now());
        sysMessagesMapper.insert(sysMessages);
    }

    /**
     * 查询系统消息，通过用户id
     * @param userId 用户id
     * @return 系统消息
     * @author Mr.Deng
     * @date 10:34 2018/12/19
     */
    public List<SysMessages> listByUserId(Integer userId) {
        EntityWrapper<SysMessages> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return sysMessagesMapper.selectList(wrapper);
    }

    /**
     * 分页查询系统消息，通过用户id
     * @param userId 用户id
     * @return 系统消息
     * @author Mr.Deng
     * @date 10:34 2018/12/19
     */
    public  Page<SysMessages> listByUserIdPage(Integer userId, Integer pageNum, Integer pageSize) {
        EntityWrapper<SysMessages> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        Page<SysMessages> page = new Page<>(pageNum, pageSize);
        List<SysMessages> sysMessages = sysMessagesMapper.selectPage(page, wrapper);
        page.setRecords(sysMessages);
        return page;
    }

    /**
     * 统计数量
     * @param userId userId
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/12/29 10:09
     * @company mitesofor
    */
    public Integer countNum(Integer userId){
        EntityWrapper<SysMessages> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return sysMessagesMapper.selectCount(wrapper);
    }

    /**
     * 统计未独数量
     * @param userId userId
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/12/29 10:10
     * @company mitesofor
    */
    public Integer countNotReadNum(Integer userId){
        Integer num = sysMessageReadService.countNum(userId);
        Integer sum = this.countNum(userId);
        return sum - num;
    }

    /**
     * 添加系统消息
     * @param cellphone 手机号
     * @param title     标题
     * @param details   详情
     * @author Mr.Deng
     * @date 10:44 2018/12/19
     */
    public void addSysMessages(String cellphone, String title, String details) {
        User user = userService.getByCellphone(cellphone);
        if (user != null) {
            SysMessages sysMessages = new SysMessages(user.getId(), title, details, LocalDateTime.now());
            this.save(sysMessages);
            log.info(user.getCellphone() + "-系统消息添加成功-" + LocalDateTime.now());
        } else {
            log.error(cellphone + "-系统消息添加失败-" + LocalDateTime.now());
        }
    }
}
