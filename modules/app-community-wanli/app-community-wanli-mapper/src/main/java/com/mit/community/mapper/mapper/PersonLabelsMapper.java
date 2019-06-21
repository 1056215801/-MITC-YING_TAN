package com.mit.community.mapper.mapper;

import org.apache.ibatis.annotations.Param;

public interface PersonLabelsMapper {
    public void saveLabels(@Param("labels") String labels, @Param("userId")Integer userId);
    public String getLabelsByHousehold(@Param("household")Integer household);
    public int getRkcfByIdNum(@Param("idNum")String idNum);
}
