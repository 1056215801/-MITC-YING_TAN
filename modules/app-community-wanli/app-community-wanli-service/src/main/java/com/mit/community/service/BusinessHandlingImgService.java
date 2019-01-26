package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.BusinessHandlingImg;
import com.mit.community.mapper.BusinessHandlingImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务办理图片业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 13:55
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BusinessHandlingImgService extends ServiceImpl<BusinessHandlingImgMapper, BusinessHandlingImg> {
    @Autowired
    private BusinessHandlingImgMapper bussinessHandingImgMapper;

    /**
     * 添加业务图片
     * @param bussinessHandingImg 业务图片信息
     * @return 添加条数
     * @author Mr.Deng
     * @date 13:56 2018/12/5
     */
    public Integer save(BusinessHandlingImg bussinessHandingImg) {
        bussinessHandingImg.setGmtCreate(LocalDateTime.now());
        bussinessHandingImg.setGmtModified(LocalDateTime.now());
        return bussinessHandingImgMapper.insert(bussinessHandingImg);
    }

    /**
     * 查询业务办理图片信息
     * @param businessHandlingId 业务办理id
     * @return 业务办理图片信息
     * @author Mr.Deng
     * @date 19:33 2018/12/19
     */
    public List<BusinessHandlingImg> getByBusinessHandlingId(Integer businessHandlingId) {
        EntityWrapper<BusinessHandlingImg> wrapper = new EntityWrapper<>();
        wrapper.eq("business_handling_id", businessHandlingId);
        return bussinessHandingImgMapper.selectList(wrapper);
    }

    /**
     * 查询业务办理图片信息，组
     * @param businessHandlingIds 一组业务办理id
     * @return 业务办理图片
     * @author Mr.Deng
     * @date 11:56 2019/1/24
     */
    public List<BusinessHandlingImg> getByBusinessHandlingIds(List<Integer> businessHandlingIds) {
        EntityWrapper<BusinessHandlingImg> wrapper = new EntityWrapper<>();
        wrapper.in("business_handling_id", businessHandlingIds);
        return bussinessHandingImgMapper.selectList(wrapper);
    }

}
