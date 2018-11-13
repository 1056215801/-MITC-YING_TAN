package com.mit.admin.biz;

import com.mit.admin.entity.GroupType;
import org.springframework.stereotype.Service;

import com.mit.admin.mapper.GroupTypeMapper;
import com.mit.common.biz.BaseBiz;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:19
 * @company mitesofor
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper, GroupType> {
}
