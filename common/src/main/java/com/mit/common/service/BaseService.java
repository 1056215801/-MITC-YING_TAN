package com.mit.common.service;

import java.util.List;

/**
 * base service
 *
 * @author shuyy
 * @date 2018/11/8 8:47
 * @company mitesofor
 */
public interface BaseService<T> {
    /**
     * 查询
     *
     * @param entity 实体
     * @return T
     * @author shuyy
     * @date 2018/11/8 8:48
     * @company mitesofor
     */
    T selectOne(T entity);

    /**
     * 通过Id查询
     *
     * @param id
     * @return
     * @author shuyy
     * @date 2018/11/8 8:48
     * @company mitesofor
     */

    T selectById(Object id);

    /**
     * 根据ID集合来查询
     *
     * @param ids
     * @return
     */
//    List<T> selectListByIds(List<Object> ids);

    /**
     * 查询列表
     *
     * @param entity
     * @return
     * @author shuyy
     * @date 2018/11/8 8:49
     * @company mitesofor
     */
    List<T> selectList(T entity);

    /**
     * 获取所有对象
     *
     * @return
     * @author shuyy
     * @date 2018/11/8 8:49
     * @company mitesofor
     */
    List<T> selectListAll();

    /**
     * 查询总记录数
     * @author shuyy
     * @date 2018/11/8 8:49
     * @company mitesofor
     */
//    Long selectCountAll();

    /**
     * 查询总记录数
     *
     * @param entity
     * @return
     * @author shuyy
     * @date 2018/11/8 8:49
     * @company mitesofor
     */
    Long selectCount(T entity);

    /**
     * 添加
     *
     * @param entity 实体
     * @author shuyy
     * @date 2018/11/8 8:50
     * @company mitesofor
     */
    void insert(T entity);

    /**
     * 插入 不插入null字段
     *
     * @param entity 实体
     * @author shuyy
     * @date 2018/11/8 8:50
     * @company mitesofor
     */
    void insertSelective(T entity);

    /**
     * 删除
     *
     * @param entity entity
     * @author shuyy
     * @date 2018/11/8 8:50
     * @company mitesofor
     */
    void delete(T entity);

    /**
     * 根据Id删除
     *
     * @param id id
     * @author shuyy
     * @date 2018/11/8 8:51
     * @company mitesofor
     */
    void deleteById(Object id);

    /**
     * 根据id更新
     *
     * @param entity entity
     * @author shuyy
     * @date 2018/11/8 8:51
     * @company mitesofor
     */
    void updateById(T entity);

    /**
     * 不update null
     *
     * @param entity entity
     * @author shuyy
     * @date 2018/11/8 8:53
     * @company mitesofor
     */
    void updateSelectiveById(T entity);

    /*
//    void deleteBatchByIds(List<Object> ids);


    /**
     * 批量更新
     *
     * @param entitys
     */
//    void updateBatch(List<T> entitys);
}
