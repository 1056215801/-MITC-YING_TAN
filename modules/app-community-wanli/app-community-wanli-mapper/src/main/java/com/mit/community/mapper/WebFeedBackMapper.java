package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.WebFeedBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface WebFeedBackMapper extends BaseMapper<WebFeedBack> {

    List<WebFeedBack> selectTestPage(RowBounds rowBounds, @Param("ew") Wrapper<WebFeedBack> wrapper);

    void updateFeedback(@Param("status") Integer status, @Param("handler") Integer handler,
                        @Param("handletime") Date handletime, @Param("remark") String remark,
                        @Param("id") Integer id);
}
