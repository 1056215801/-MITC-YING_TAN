package com.mit.cloud.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 测试
 *
 * @author shuyy
 * @date 2018/11/9
 * @company mitesofor
 */
@TableName("test_book")
@Data
public class TestBook {

    @TableId(type = IdType.AUTO)
    private  Integer id;

    private String name;

    private  Integer price;

    private  String author;

}
