package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生活黄页表
 * @author Mr.Deng
 * @date 2018/12/5 17:12
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yellow_pages")
public class YellowPages extends BaseEntity {
    /**
     * 黄页类型id，关联yellow_pages_type表id
     */
    @TableField("yellow_pages_type_id")
    private Integer yellowPagesTypeId;

    /**
     * 电话备注名称
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;

}
