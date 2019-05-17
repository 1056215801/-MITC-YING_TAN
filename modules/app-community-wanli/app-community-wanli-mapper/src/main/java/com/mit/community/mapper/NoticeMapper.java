package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.Notice;
import com.mit.community.entity.NoticeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Mr.Deng
 * @date 2018/12/3 14:36
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> selectMyPage(RowBounds rowBounds, @Param("ew") Wrapper<Notice> wrapper);

    NoticeVo getNoticeById(Integer id);

    void deleteNoticeById(Integer id);

    void stopOrEnable(@Param("status") Integer status, @Param("id") Integer id);
}
