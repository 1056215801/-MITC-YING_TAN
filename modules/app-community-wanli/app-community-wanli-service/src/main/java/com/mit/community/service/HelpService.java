package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Help;
import com.mit.community.mapper.HelpMapper;
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
    public void save(String title, String content, Short orders) {
        Help help = new Help(title, content, orders);
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
    public void update(Integer id, String title, String content, Short orders) {
        Help help = new Help(title, content, orders);
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

}
