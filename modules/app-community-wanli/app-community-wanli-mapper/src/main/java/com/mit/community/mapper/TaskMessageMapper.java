package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.TaskMessage;

import java.util.List;

public interface TaskMessageMapper extends BaseMapper<TaskMessage> {
    List<TaskMessage> getTargetList();
}
