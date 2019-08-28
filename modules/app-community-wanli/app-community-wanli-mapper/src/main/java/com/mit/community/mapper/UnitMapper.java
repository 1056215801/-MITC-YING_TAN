package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Building;
import com.mit.community.entity.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Mr.Deng
 * @date 2018/12/7 18:17
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface UnitMapper extends BaseMapper<Unit> {

    List<Unit> getUnitList(RowBounds rowBounds, @Param("ew") EntityWrapper<Unit> wrapper);
}
