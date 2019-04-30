package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Help;
import com.mit.community.mapper.HelpMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帮助
 *
 * @author shuyy
 * @date 2019-01-03
 * @company mitesofor
 */
@Service
public class HelpService {
    @Autowired
    private HelpMapper helpMapper;

    /**
     * 保存
     *
     * @param title   标题
     * @param content 内容
     * @author shuyy
     * @date 2019-01-03 11:38
     * @company mitesofor
     */
    public void save(String title, String content, Short orders, Integer status) {
        Help help = new Help(title, content, orders, status);
        help.setGmtCreate(LocalDateTime.now());
        help.setGmtModified(LocalDateTime.now());
        helpMapper.insert(help);
    }

    /**
     * 更新
     * @param id id
     * @param title 标题
     * @param content 内容
     * @author shuyy
     * @date 2019-01-03 11:40
     * @company mitesofor
     */
    public void update(Integer id, String title, String content, Short orders, Integer status) {
        Help help = new Help(title, content, orders, status);
        help.setId(id);
        help.setGmtModified(LocalDateTime.now());
        helpMapper.updateById(help);
    }

    /**
     * 删除
     * @param id id
     * @author shuyy
     * @date 2019-01-03 11:42
     * @company mitesofor
    */
    public void remove(Integer id){
        helpMapper.deleteById(id);
    }
    /**
     * 查询
     * @return java.util.List<com.mit.community.entity.Help>
     * @author shuyy
     * @date 2019-01-03 11:42
     * @company mitesofor
    */
    public List<Help> list(){
        EntityWrapper<Help> wrapper = new EntityWrapper<>();
        wrapper.orderBy("orders");
        return helpMapper.selectList(null);
    }

    public Page<Help> listPage(String title, Integer status, LocalDateTime gmtCreateTimeStart, LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        EntityWrapper<Help> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(title)) {
            wrapper.eq("title", title);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("gmt_create", gmtCreateTimeEnd);
        }
        Page<Help> page = new Page<>(pageNum, pageSize);
        List<Help> helps = helpMapper.selectPage(page, wrapper);
        page.setRecords(helps);
        return page;
    }

}
