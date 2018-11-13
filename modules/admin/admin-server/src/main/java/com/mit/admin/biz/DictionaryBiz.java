package com.mit.admin.biz;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mit.admin.entity.Dictionary;
import com.mit.admin.mapper.DictionaryMapper;
import com.mit.common.biz.BaseBiz;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 *
 * @author shuyy
 * @email 490899514@qq.com
 * @date 2018-08-11 06:47:40
 */
@Service
public class DictionaryBiz extends BaseBiz<DictionaryMapper,Dictionary> {
	
	/**
	* 通过父code，获取所有的字典
	* @param @param code
	* @param @return
	* @return List<Dictionary>
	* @throws
	 */
	public List<Dictionary> selectByCode(String code) {
        Class<Dictionary> clazz = (Class<Dictionary>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentCode", code);
        List<Dictionary> list = mapper.selectByExample(example);
        return list;
    }
}