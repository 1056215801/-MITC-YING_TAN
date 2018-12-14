package com.mit.community.service;

import com.mit.community.entity.BusinessHandlingImg;
import com.mit.community.mapper.BusinessHandlingImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 业务办理图片业务处理层
 * @author Mr.Deng
 * @date 2018/12/5 13:55
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BusinessHandlingImgService {
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

}
