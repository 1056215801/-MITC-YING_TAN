package com.mit.community.mapper.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.entity.InfoSearch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface InfoSearchMapper {
    List<InfoSearch> selectPersonBaseInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectAzbInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectBearInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectCxInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectEngPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectFlowPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectMilitaryServiceInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectPartyInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectSfInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectSqjzpeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectStayPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectXdPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectXmsfPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectZdqsnPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectZszhPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectLdzPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectWgyPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectZyzPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectOldPeopleInfoPage(RowBounds rowBounds, @Param("ew") Wrapper<InfoSearch> wrapper);

    List<InfoSearch> selectPersonBaseInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectAzbInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectBearInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectCxInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectEngPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectFlowPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectMilitaryServiceInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectPartyInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectSfInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectSqjzpeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectStayPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectXdPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectXmsfPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectZdqsnPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);
    List<InfoSearch> selectZszhPeopleInfo(@Param("ew") Wrapper<InfoSearch> wrapper);


    public String getByPhone(@Param("phone")String phone);
}
