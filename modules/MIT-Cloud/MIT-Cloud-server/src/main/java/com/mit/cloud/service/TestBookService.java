package com.mit.cloud.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.cloud.dao.TestBookMapper;
import com.mit.cloud.model.TestBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 *
 * @author shuyy
 * @date 2018/11/9
 * @company mitesofor
 */
@Service
public class TestBookService {

    @Autowired
    private TestBookMapper testBookMapper;

    public void insert(){
        TestBook testBook = new TestBook();
        testBook.setName("舒园园");
        testBook.setAuthor("金庸");
        testBook.setPrice(1000);
        testBookMapper.insert(testBook);
        if(testBook != null){
            throw new RuntimeException("错误");
        }
    }

    public Page<TestBook> listPage(Integer pageNum, Integer pageSize){
        Page<TestBook> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        testBookMapper.selectPage(page, null);
        return null;
    }


}
