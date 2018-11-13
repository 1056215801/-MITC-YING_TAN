package com.mit.admin.biz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mit.admin.entity.TestPerson;
import com.mit.admin.mapper.TestPersonMapper;
import com.mit.common.biz.BaseBiz;

@Service
public class TestPersonBizc extends BaseBiz<TestPersonMapper, TestPerson>{
	
	@Override
    @Transactional
	public void insert(TestPerson entity) {
        mapper.insert(entity);
    }

}
