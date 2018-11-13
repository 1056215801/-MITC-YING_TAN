package com.mit.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shuyy
 * @email 490899514@qq.com
 * @date 2018-08-11 06:47:40
 */
@Table(name = "base_dictionary")
@Data
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 名
     */
    @Column(name = "name")
    private String name;

    /**
     * 编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 排序
     */
    @Column(name = "order_")
    private Integer order;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 父code
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 是否有子节点。0：没有。1：有
     */
    @Column(name = "havue_children")
    private Integer havueChildren;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人id
     */
    @Column(name = "update_user_id")
    private Integer updateUserId;

    /**
     * 更新人名称
     */
    @Column(name = "update_user_name")
    private String updateUserName;

}
