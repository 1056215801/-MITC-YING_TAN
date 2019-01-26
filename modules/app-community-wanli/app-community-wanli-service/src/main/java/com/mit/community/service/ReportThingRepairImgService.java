package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class ReportThingRepairImgService extends ServiceImpl<ReportThingRepairImgMapper, ReportThingRepairImg> {
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
     * @param reportThingsRepairId 报事报修id
     * @return 报事报修图片信息
     * @author Mr.Deng
     * @date 18:49 2018/12/19
     */
    public List<ReportThingRepairImg> getByReportThingsRepairId(Integer reportThingsRepairId) {
        EntityWrapper<ReportThingRepairImg> wrapper = new EntityWrapper<>();
        wrapper.eq("report_thing_repair_id", reportThingsRepairId);
        return reportThingRepairImgMapper.selectList(wrapper);
    }

    /**
     * 查询报事报修图片，通过报事报修id列表
     * @param reportThingsRepairIds 报事报修ids
     * @return 报事报修图片
     * @author Mr.Deng
     * @date 14:15 2019/1/25
     */
    public List<ReportThingRepairImg> getByReportThingsRepairIds(List<Integer> reportThingsRepairIds) {
        EntityWrapper<ReportThingRepairImg> wrapper = new EntityWrapper<>();
        wrapper.in("report_thing_repair_id", reportThingsRepairIds);
        return reportThingRepairImgMapper.selectList(wrapper);
    }

}
