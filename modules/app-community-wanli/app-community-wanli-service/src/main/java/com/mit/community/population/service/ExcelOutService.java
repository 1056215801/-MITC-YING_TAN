package com.mit.community.population.service;

import com.mit.community.entity.LdzExcelInfo;
import com.mit.community.entity.OldExcelInfo;
import com.mit.community.entity.ZyzExcelInfo;
import com.mit.community.entity.entity.*;
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

    public List<PartyExcelInfo> getPartyExcel(){
        List<PartyExcelInfo> list = excelOutMapper.getPartyExcel();
        return list;
    }

    public List<SFPeopleExcelInfo> getSfExcel(){
        List<SFPeopleExcelInfo> list = excelOutMapper.getSfExcel();
        return list;
    }

    public List<StayPeopleExcelInfo> getStayExcel(){
        List<StayPeopleExcelInfo> list = excelOutMapper.getStayExcel();
        return list;
    }

    public List<XdExcelInfo> getXdExcel(){
        List<XdExcelInfo> list = excelOutMapper.getXdExcel();
        return list;
    }

    public List<XmsfExcelInfo> getXmsfExcel(){
        List<XmsfExcelInfo> list = excelOutMapper.getXmsfExcel();
        return list;
    }

    public List<ZDQSNCExcelInfo> getZdqsnExcel(){
        List<ZDQSNCExcelInfo> list = excelOutMapper.getZdqsnExcel();
        return list;
    }

    public List<ZSZHExcelInfo> getZszhExcel(){
        List<ZSZHExcelInfo> list = excelOutMapper.getZszhExcel();
        return list;
    }

    public List<LdzExcelInfo> getLdzExcel(){
        List<LdzExcelInfo> list = excelOutMapper.getLdzExcel();
        return list;
    }

    public List<OldExcelInfo> getOldExcel(){
        List<OldExcelInfo> list = excelOutMapper.getOldExcel();
        return list;
    }

    public List<LdzExcelInfo> getWgyExcel(){
        List<LdzExcelInfo> list = excelOutMapper.getWgyExcel();
        return list;
    }

    public List<ZyzExcelInfo> getZyzExcel(){
        List<ZyzExcelInfo> list = excelOutMapper.getZyzExcel();
        return list;
    }

    public List<CensusExcelInfo> getHjrkExcel(){
        List<CensusExcelInfo> list = excelOutMapper.getHjrkExcel();
        return list;
    }

    public List<FlowPeopleExcelInfo> getLdExcel(){
        List<FlowPeopleExcelInfo> list = excelOutMapper.getLdExcel();
        return list;
    }

    public List<HouseExcelInfo> getHouseExcel(){
        List<HouseExcelInfo> list = excelOutMapper.getHouseExcel();
        return list;
    }

    public List<CarExcelInfo> getCarExcel(String accountType, String streetName, String areaName){
        List<CarExcelInfo> list = excelOutMapper.getCarExcel();
        return list;
    }

    public List<MilitaryServiceExcelInfo> getMilitaryExcel(){
        List<MilitaryServiceExcelInfo> list = excelOutMapper.getMilitaryExcel();
        return list;
    }
}
