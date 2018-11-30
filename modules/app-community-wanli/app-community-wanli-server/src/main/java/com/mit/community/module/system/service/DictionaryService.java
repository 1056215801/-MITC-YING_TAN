package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Dictionary;
import com.mit.community.module.system.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class DictionaryService {

    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryService(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    /**
     * 查询数据字典， 通过parentCode
     * @param parentCode parentCode
     * @return java.util.List<com.mit.community.entity.Dictionary>
     * @author shuyy
     * @date 2018/11/30 10:11
     * @company mitesofor
    */
    public List<Dictionary> listByParentCode(String parentCode) {
        EntityWrapper<Dictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_code", parentCode);
        return dictionaryMapper.selectList(wrapper);
    }

    /**
     * 查询所有的数据字典
     * @return java.util.List<com.mit.community.entity.Dictionary>
     * @author shuyy
     * @date 2018/11/30 10:15
     * @company mitesofor
    */
    public List<Dictionary> list(){
        return dictionaryMapper.selectList(null);
    }



}
