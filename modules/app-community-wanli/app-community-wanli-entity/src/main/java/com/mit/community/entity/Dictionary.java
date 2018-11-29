package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
public class Dictionary extends BaseEntity implements Serializable {

    private String name;

    private String code;

    /**
     * 排序
     */
    @TableField("order_")
    private Integer order;

    /**
     * 父id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 父code
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 是否有子节点。0：没有。1：有
     */
    @TableField("havue_children")
    private Integer havueChildren;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 更新人id
     */
    @TableField("update_user_id")
    private Integer updateUserId;

    /**
     * 更新人名称
     */
    @TableField("update_user_name")
    private String updateUserName;

}
