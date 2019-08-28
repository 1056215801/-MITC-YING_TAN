package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ClusterCommunity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 小区
 *
 * @author Mr.Deng
 * @date 2018/11/14 13:44
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */

public interface ClusterCommunityMapper extends BaseMapper<ClusterCommunity> {

    List<ClusterCommunity> selectMyPage(RowBounds rowBounds, @Param("ew") EntityWrapper<ClusterCommunity> wrapper, @Param("username") String username);
}
