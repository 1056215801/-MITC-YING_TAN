package com.mit.community.mapper.mapper;

import com.mit.community.entity.*;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonLabelsMapper {
    public void saveLabels(@Param("labels") String labels, @Param("userId")Integer userId);
    public String getLabelsByHousehold(@Param("household")Integer household);
    public int getRkcfByIdNum(@Param("idNum")String idNum);
    public Integer getSirPersonBaseInfoId(@Param("id")Integer id);
    public TaskMessageSirInfo getSirUserIdBySirPersonBaseInfoId(@Param("person_baseinfo_id")Integer person_baseinfo_id);
    public TaskMessageContent getTaskMessageContent(@Param("reportProblemId")Integer reportProblemId, @Param("wgyId")Integer wgyId);
    public int getPeopleCount();
    public int getOutCount();
    public String getSirPhoneByPersonBaseInfoId(@Param("person_baseinfo_id")Integer person_baseinfo_id);
    public void updateMqlzd(@Param("id")Integer id);
    public List<PeopleOut> getPeopleOue();
    public String getMenJinTime(@Param("name")String name);
    public String getContentByTime(@Param("time")LocalDateTime time);
    public List<MenJinInfo> getMenJinList(@Param("name")String name);
    public List<WgyInfo> getWgyList(@Param("wgyId")Integer wgyId);
    public String getWgyDeptById(@Param("id")Integer id);
}
