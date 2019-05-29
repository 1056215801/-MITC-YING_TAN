package com.mit.community.util;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

/**
 * 
* 分页工具类。对于特定的分页数据的包装。比如：Page类的records只能包装Page的实体类， 比如使用selectMap, 返回一个List<Map<>>类型， 则可以使用这个分页类封装
* @author shuyy
* @date 2018年9月6日
* @param <T>
 */
@Data
public class PageObject<T> extends Page<T> {
	
	private Object object;

	
}
