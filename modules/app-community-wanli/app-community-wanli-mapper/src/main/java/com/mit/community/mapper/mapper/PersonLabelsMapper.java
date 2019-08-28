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
    String getRkcfByMobile(@Param("mobile") String mobile);

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
    List<OwnerShipInfo> getOwnerInfo(@Param("cph")String cph);
    List<DeviceInfo> selectMenJinPage(RowBounds rowBounds, @Param("ew")EntityWrapper<DeviceInfo> wrapper, @Param("sql")String sql);
    List<DnakeDeviceDetailsInfo> getUnBindDevice(@Param("ip")String ip);
<<<<<<< HEAD
<<<<<<< HEAD
    List<DeviceBugPeople> pageDeviceDugPeople(RowBounds rowBounds, @Param("ew")EntityWrapper<DeviceBugPeople> wrapper);
    List<DeviceInfo> getDevicesByDeviceGroupId(@Param("deviceGroupId")Integer deviceGroupId);
    AccessCard getByCardNumAndMac (@Param("cardNum")String cardNum, @Param("mac")String mac);
    String getIsOnline(@Param("deviceNum")String deviceNum);
=======
>>>>>>> remotes/origin/newdev
=======
    List<DeviceBugPeople> pageDeviceDugPeople(RowBounds rowBounds, @Param("ew")EntityWrapper<DeviceBugPeople> wrapper);
    List<DeviceInfo> getDevicesByDeviceGroupId(@Param("deviceGroupId")Integer deviceGroupId);
    AccessCard getByCardNumAndMac (@Param("cardNum")String cardNum, @Param("mac")String mac);
    DeviceIsOnline getIsOnline(@Param("deviceNum")String deviceNum);
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
}
