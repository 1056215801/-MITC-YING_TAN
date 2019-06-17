package com.mit.community.population.service;

import com.mit.community.entity.entity.AzbExcelInfo;
import com.mit.community.entity.entity.BearExcelInfo;
import com.mit.community.entity.entity.CXExcelInfo;
import com.mit.community.entity.entity.EngPeopleExcelInfo;
import com.mit.community.mapper.ExcelOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelOutService {
    @Autowired
    private ExcelOutMapper excelOutMapper;

    public List<AzbExcelInfo> getAzbExcel(){
        List<AzbExcelInfo> list = excelOutMapper.getAzbExcel();
        return list;
    }

    public List<BearExcelInfo> getBearExcel(){
        List<BearExcelInfo> list = excelOutMapper.getBearExcel();
        return list;
    }

    public List<CXExcelInfo> getCXExcel(){
        List<CXExcelInfo> list = excelOutMapper.getCXExcel();
        return list;
    }

    public List<EngPeopleExcelInfo> getEngPeopleExcel(){
        List<EngPeopleExcelInfo> list = excelOutMapper.getEngPeopleExcel();
        return list;
    }
}
