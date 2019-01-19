package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.VisitorMessage;
import com.mit.community.mapper.VisitorMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客消息service
 *
 * @author shuyy
 * @date 2019-01-02
 * @company mitesofor
 */
@Service
public class VisitorMessageService extends ServiceImpl<VisitorMessageMapper, VisitorMessage> {

    @Autowired
    private VisitorMessageMapper visitorMessageMapper;


    /**
     * 查询未读
     *
     * @param mobile
     * @return java.lang.Integer
     * @throws
     * @author shuyy
     * @date 2019-01-03 10:28
     * @company mitesofor
     */
    public Integer countNotRead(String mobile) {
        EntityWrapper<VisitorMessage> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", mobile);
        wrapper.eq("status", 1);
        return visitorMessageMapper.selectCount(wrapper);
    }

    /**
     * 分页查询
     * @param cellphone
     * @param pageNum
     * @param pageSize
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.entity.VisitorMessage>
     * @throws
     * @author shuyy
     * @date 2019-01-03 10:33
     * @company mitesofor
    */
    public Page<VisitorMessage> listPage(String cellphone, Integer pageNum, Integer pageSize){
        EntityWrapper<VisitorMessage> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", cellphone);
        wrapper.orderBy("gmt_create", false);
        Page<VisitorMessage> page = new Page(pageNum, pageSize);
        List<VisitorMessage> visitorMessages = visitorMessageMapper.selectPage(page, wrapper);
        page.setRecords(visitorMessages);
        return page;
    }

    /**
     * 更新状态
     * @param visitorMessages
     * @return void
     * @throws
     * @author shuyy
     * @date 2019-01-03 10:41
     * @company mitesofor
    */
    public void updateStatus(List<VisitorMessage> visitorMessages){
        visitorMessages.forEach(item -> item.setStatus((short) 2));
        this.updateBatchById(visitorMessages);
    }
}
