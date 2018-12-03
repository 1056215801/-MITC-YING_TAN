package com.mit.community.service;

import com.mit.community.entity.ApplyKeyImg;
import com.mit.community.mapper.ApplyKeyImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 申请钥匙图片业务层
 * @author Mr.Deng
 * @date 2018/12/3 14:48
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ApplyKeyImgService {

    @Autowired
    private ApplyKeyImgMapper applyKeyImgMapper;

    /**
     * 添加申请钥匙图片信息
     * @param applyKeyImg 申请钥匙图片信息
     * @return 添加数量
     * @author Mr.Deng
     * @date 11:03 2018/11/30
     */
    public Integer save(ApplyKeyImg applyKeyImg) {
        applyKeyImg.setGmtCreate(LocalDateTime.now());
        applyKeyImg.setGmtModified(LocalDateTime.now());
        return applyKeyImgMapper.insert(applyKeyImg);
    }

}
