package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Building;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Mr.Deng
 * @date 2018/12/7 18:06
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface BuildingMapper extends BaseMapper<Building> {
    List<Building> selectBuildingPage(RowBounds rowBounds, @Param("ew") EntityWrapper<Building> wrapper);
}
