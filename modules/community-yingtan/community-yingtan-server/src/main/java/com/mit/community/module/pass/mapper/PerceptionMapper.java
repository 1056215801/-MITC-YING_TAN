package com.mit.community.module.pass.mapper;

import com.mit.community.entity.Current;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerceptionMapper {
    List<Current> getSjByDay(@Param("type")String type, @Param("dateNowStr")String dateNowStr, @Param("list")List<String> list);
    List<Current> getXqByDay(@Param("dateNowStr")String dateNowStr, @Param("list")List<String> list);

    List<Current> getSjByMonth(@Param("type")String type, @Param("dateNowStr")String dateNowStr, @Param("list")List<String> list);
    List<Current> getXqByMonth(@Param("dateNowStr")String dateNowStr, @Param("list")List<String> list);


    List<Current> getXfBySomeDay(@Param("dayBefore")String dayBefore,@Param("dateNowStr")String dateNowStr,@Param("list")List<String> list);
    List<Current> getMwgBySomeDay(@Param("dayBefore")String dayBefore,@Param("dateNowStr")String dateNowStr,@Param("list")List<String> list);
    int getYgStatusByStatus(@Param("status")int status,@Param("list")List<String> list);
    int getYgWarnByStatus(@Param("list")List<String> list);

    int getJgStatusByStatus(@Param("status")int status,@Param("list")List<String> list);
    int getJgWarnByStatus(@Param("list")List<String> list);

    int getMjCount(@Param("list")List<String> list);
    int getJgCount(@Param("list")List<String> list);
    int getYgCount(@Param("list")List<String> list);
    int getPerceptionService(@Param("list")List<String> list);
}
