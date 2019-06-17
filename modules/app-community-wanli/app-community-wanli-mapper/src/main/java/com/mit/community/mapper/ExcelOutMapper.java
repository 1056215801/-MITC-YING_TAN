package com.mit.community.mapper;

import com.mit.community.entity.entity.AzbExcelInfo;
import com.mit.community.entity.entity.BearExcelInfo;
import com.mit.community.entity.entity.CXExcelInfo;
import com.mit.community.entity.entity.EngPeopleExcelInfo;

import java.util.List;

public interface ExcelOutMapper {
    public List<AzbExcelInfo> getAzbExcel();
    public List<BearExcelInfo> getBearExcel();
    public List<CXExcelInfo> getCXExcel();
    public List<EngPeopleExcelInfo> getEngPeopleExcel();
}
