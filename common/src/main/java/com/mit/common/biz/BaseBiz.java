package com.mit.common.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mit.common.config.DataSourceKey;
import com.mit.common.config.TargetDataSource;
import com.mit.common.msg.TableResultResponse;
import com.mit.common.util.EntityUtils;
import com.mit.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * biz基础类
 *
 * @author shuyy
 * @date 2018/11/7 17:00
 * @company mitesofor
 */
public abstract class BaseBiz<M extends Mapper<T>, T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    //    @TargetDataSource(DataSourceKey.DATA_SOURCE_2)
    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    public void insert(T entity) {
        EntityUtils.setCreateAndUpdateInfo(entity);
        mapper.insert(entity);
    }


    public void insertSelective(T entity) {
        EntityUtils.setCreateAndUpdateInfo(entity);
        mapper.insertSelective(entity);
    }


    public void delete(T entity) {
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }


    public void updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public TableResultResponse<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if (query.entrySet().size() > 0) {
            //查询条件是or还是and
            boolean orQuery = false;
            String condition = "orQuery";
            if (null != query.get(condition)) {
                query.remove(condition);
                orQuery = true;
            }
            int i = 0;
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if (orQuery && i++ > 0) {
                    Example.Criteria orCriteria = example.createCriteria();
                    orCriteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                    example.or(orCriteria);
                } else {
                    criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                }
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }

}
