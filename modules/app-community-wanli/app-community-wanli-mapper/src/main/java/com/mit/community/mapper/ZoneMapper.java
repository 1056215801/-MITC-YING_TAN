package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.Zone;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.Deng
 * @date 2018/12/7 17:50
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface ZoneMapper extends BaseMapper<Zone> {

    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);
}
