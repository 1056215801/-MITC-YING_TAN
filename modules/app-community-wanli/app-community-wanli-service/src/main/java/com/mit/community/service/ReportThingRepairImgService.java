package com.mit.community.service;

import com.mit.community.entity.ReportThingRepairImg;
import com.mit.community.mapper.ReportThingRepairImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 报事报修图片业务处理层
 * @author Mr.Deng
 * @date 2018/12/3 19:43
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ReportThingRepairImgService {
    @Autowired
    private ReportThingRepairImgMapper reportThingRepairImgMapper;

    /**
     * 添加报事报修图片
     * @param reportThingRepairImg 报事报修图片
     * @return 添加数据
     * @author Mr.Deng
     * @date 19:44 2018/12/3
     */
    public Integer save(ReportThingRepairImg reportThingRepairImg) {
        reportThingRepairImg.setGmtCreate(LocalDateTime.now());
        reportThingRepairImg.setGmtModified(LocalDateTime.now());
        return reportThingRepairImgMapper.insert(reportThingRepairImg);
    }

}
