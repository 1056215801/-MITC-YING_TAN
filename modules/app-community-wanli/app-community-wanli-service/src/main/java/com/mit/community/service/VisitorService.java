package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Visitor;
import com.mit.community.entity.VisitorImg;
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
    @Autowired
    private VisitorImgService visitorImgService;

    /**
     * 查询所有访客信息
     * @return 访客信息列表
     * @author Mr.Deng
     * @date 17:16 2018/12/3
     */
    public Page<Visitor> listPage(String cellphone, Integer pageNum, Integer pageSize) {
        EntityWrapper<Visitor> wrapper = new EntityWrapper<>();
        wrapper.eq("invite_mobile", cellphone);
        Page<Visitor> page = new Page<>(pageNum, pageSize);
        List<Visitor> visitors = visitorMapper.selectPage(page, wrapper);
        page.setRecords(visitors);
        return page;
    }

    /**
     * 获取访客详情
     * @param id
     * @return com.mit.community.entity.Visitor
     * @throws
     * @author shuyy
     * @date 2018/12/29 10:44
     * @company mitesofor
    */
    public Visitor getById(Integer id){
        Visitor visitor = visitorMapper.selectById(id);
        if(visitor != null){
            List<VisitorImg> visitorImgs = visitorImgService.listByVisitorId(id);
            visitor.setVisitorImgList(visitorImgs);
            return visitor;
        }
        return null;
    }

}
