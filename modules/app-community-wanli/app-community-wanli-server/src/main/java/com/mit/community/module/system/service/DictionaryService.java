package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Dictionary;
import com.mit.community.module.system.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /**
     * 添加数据字典
     * @param dictionary 数据字典内容
     * @author Mr.Deng
     * @date 8:53 2018/12/12
     */
    public void save(Dictionary dictionary) {
        dictionary.setGmtCreate(LocalDateTime.now());
        dictionary.setGmtModified(LocalDateTime.now());
        dictionaryMapper.insert(dictionary);
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
     * 获取字典，通过code
     * @param code code
     * @return com.mit.community.entity.Dictionary
     * @author shuyy
     * @date 2018/12/12 16:55
     * @company mitesofor
    */
    public Dictionary getByCode(String code){
        EntityWrapper<Dictionary> wrapper = new EntityWrapper<>();
        wrapper.eq("code", code);
        List<Dictionary> dictionaries = dictionaryMapper.selectList(wrapper);
        if(dictionaries.isEmpty()){
            return null;
        }
        return dictionaries.get(0);
    }

    /**
     * 查询所有的数据字典
     * @return java.util.List<com.mit.community.entity.Dictionary>
     * @author shuyy
     * @date 2018/11/30 10:15
     * @company mitesofor
     */
    public List<Dictionary> list() {
        return dictionaryMapper.selectList(null);
    }

    /**
     * 查询字典信息，通过字典id
     * @param id id
     * @return 字典信息
     * @author Mr.Deng
     * @date 9:06 2018/12/12
     */
    public Dictionary getById(Integer id) {
        return dictionaryMapper.selectById(id);
    }

}
