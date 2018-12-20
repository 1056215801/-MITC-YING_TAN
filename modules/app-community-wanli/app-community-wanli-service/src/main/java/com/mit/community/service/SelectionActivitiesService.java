package com.mit.community.service;

import com.mit.community.mapper.SelectionActivitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 精选活动业务处理层
 * @author Mr.Deng
 * @date 2018/12/19 20:42
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class SelectionActivitiesService {
    @Autowired
    private SelectionActivitiesMapper selectionActivitiesMapper;
}
