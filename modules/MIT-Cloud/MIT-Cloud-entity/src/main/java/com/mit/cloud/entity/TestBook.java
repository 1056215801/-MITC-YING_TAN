package com.mit.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author shuyy
 * @email 490899514@qq.com
 * @date 2018-08-07 15:42:01
 */
@Table(name = "test_book")
public class TestBook implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 书名
     */
    @Column(name = "name")
    private String name;

    /**
     * 价格
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：书名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：书名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取：价格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置：作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取：作者
     */
    public String getAuthor() {
        return author;
    }
}
