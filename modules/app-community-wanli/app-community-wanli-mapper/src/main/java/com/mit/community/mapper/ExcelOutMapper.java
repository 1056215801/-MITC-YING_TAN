package com.mit.community.mapper;

import com.mit.community.entity.LdzExcelInfo;
import com.mit.community.entity.OldExcelInfo;
import com.mit.community.entity.ZyzExcelInfo;
import com.mit.community.entity.entity.*;

import java.util.List;

public interface ExcelOutMapper {
    public List<AzbExcelInfo> getAzbExcel();
    public List<BearExcelInfo> getBearExcel();
    public List<CXExcelInfo> getCXExcel();
    public List<EngPeopleExcelInfo> getEngPeopleExcel();
    public List<PartyExcelInfo> getPartyExcel();
    public List<SFPeopleExcelInfo> getSfExcel();
    public List<StayPeopleExcelInfo> getStayExcel();
    public List<XdExcelInfo> getXdExcel();
    public List<XmsfExcelInfo> getXmsfExcel();
    public List<ZDQSNCExcelInfo> getZdqsnExcel();
    public List<ZSZHExcelInfo> getZszhExcel();
    public List<LdzExcelInfo> getLdzExcel();
    public List<OldExcelInfo> getOldExcel();
    public List<LdzExcelInfo> getWgyExcel();
    public List<ZyzExcelInfo> getZyzExcel();
    public List<CensusExcelInfo> getHjrkExcel();
    public List<FlowPeopleExcelInfo> getLdExcel();
    public List<HouseExcelInfo> getHouseExcel();
    public List<CarExcelInfo> getCarExcel();
    public List<MilitaryServiceExcelInfo> getMilitaryExcel();
}
