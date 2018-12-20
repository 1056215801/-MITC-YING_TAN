package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ReportThingRepairImg;
import com.mit.community.mapper.ReportThingRepairImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 查找报事报修图片，通告报事报修id
     * @param ReportThingsRepairId 报事报修id
     * @return 报事报修图片信息
     * @author Mr.Deng
     * @date 18:49 2018/12/19
     */
    public List<ReportThingRepairImg> getByReportThingsRepairId(Integer ReportThingsRepairId) {
        EntityWrapper<ReportThingRepairImg> wrapper = new EntityWrapper<>();
        wrapper.eq("report_thing_repair_id", ReportThingsRepairId);
        return reportThingRepairImgMapper.selectList(wrapper);
    }

}
