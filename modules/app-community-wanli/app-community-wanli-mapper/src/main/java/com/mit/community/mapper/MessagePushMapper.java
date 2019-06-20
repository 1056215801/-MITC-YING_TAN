package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mit.community.entity.entity.MessageCheck;
import com.mit.community.entity.entity.MessageUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MessagePushMapper {
    List<MessageUser> getMessageUser(@Param("ew") Wrapper<MessageUser> wrapper);
    List<MessageCheck> messageAcceptListPage(RowBounds rowBounds, @Param("ew") EntityWrapper<MessageCheck> wrapper);
}
