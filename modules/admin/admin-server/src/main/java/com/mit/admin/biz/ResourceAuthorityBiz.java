package com.mit.admin.biz;

import com.mit.admin.entity.ResourceAuthority;
import com.mit.admin.mapper.ResourceAuthorityMapper;
import com.mit.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:20
 * @company mitesofor
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper,ResourceAuthority> {
}
