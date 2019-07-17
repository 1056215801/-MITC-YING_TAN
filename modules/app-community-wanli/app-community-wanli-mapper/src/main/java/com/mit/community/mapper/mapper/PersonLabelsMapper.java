package com.mit.community.mapper.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonLabelsMapper {
    public void saveLabels(@Param("labels") String labels, @Param("userId") Integer userId);

    public String getLabelsByHousehold(@Param("household") Integer household);

    public String getRkcfByIdNum(@Param("idNum") String idNum);

    public Integer getSirPersonBaseInfoId(@Param("id") Integer id);

    public TaskMessageSirInfo getSirUserIdBySirPersonBaseInfoId(@Param("person_baseinfo_id") Integer person_baseinfo_id);

    public TaskMessageContent getTaskMessageContent(@Param("reportProblemId") Integer reportProblemId, @Param("wgyId") Integer wgyId);

    public int getPeopleCount();

    public int getOutCount();


    public String getSirPhoneByPersonBaseInfoId(@Param("person_baseinfo_id") Integer person_baseinfo_id);

    public void updateMqlzd(@Param("id") Integer id);

    public List<PeopleOut> getPeopleOue();

    public String getMenJinTime(@Param("name")String name);
    public String getContentByTime(@Param("time")LocalDateTime time);
    public List<MenJinInfo> getMenJinList(@Param("name")String name);
    public List<WgyInfo> getWgyList(@Param("wgyId")Integer wgyId);
    public String getWgyDeptById(@Param("id")Integer id);
    List<DeviceGroup> selectInfoPage(RowBounds rowBounds, @Param("ew")EntityWrapper<DeviceGroup> wrapper, @Param("sql")String sql);
    public String getTimeCha(@Param("id")String id);
    String getGroupName(@Param("id")String id);
    String getMaxDeviceId();
}
