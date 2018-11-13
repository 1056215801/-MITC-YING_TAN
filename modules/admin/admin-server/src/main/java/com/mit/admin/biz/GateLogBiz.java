package com.mit.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mit.admin.entity.GateLog;
import com.mit.admin.mapper.GateLogMapper;
import com.mit.common.biz.BaseBiz;
import com.mit.common.msg.TableResultResponse;
import com.mit.common.util.Query;

import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:19
 * @company mitesofor
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BaseBiz<GateLogMapper,GateLog> {

    @Override
    public void insert(GateLog entity) {
        mapper.insert(entity);
    }

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }
    
    @Override
    public TableResultResponse<GateLog> selectByQuery(Query query) {
        Class<GateLog> clazz = (Class<GateLog>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        example.setOrderByClause("crt_time desc");
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<GateLog> list = mapper.selectByExample(example);
        return new TableResultResponse<GateLog>(result.getTotal(), list);
    }
}
