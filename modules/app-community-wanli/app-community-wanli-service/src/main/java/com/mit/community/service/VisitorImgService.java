package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.VisitorImg;
import com.mit.community.mapper.VisitorImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访客抓拍图片业务处理层
 * @author Mr.Deng
 * @date 2018/12/3 17:09
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class VisitorImgService {

    @Autowired
    private VisitorImgMapper visitorImgMapper;

    /**
     * 查询访客抓拍图片，通过访客信息id
     * @param visitorId 访客信息id
     * @return 访客抓拍图片列表
     * @author Mr.Deng
     * @date 17:13 2018/12/3
     */
    public List<VisitorImg> listByVisitorId(Integer visitorId) {
        EntityWrapper<VisitorImg> wrapper = new EntityWrapper<>();
        wrapper.eq("visitor_id", visitorId);
        return visitorImgMapper.selectList(wrapper);
    }

}
