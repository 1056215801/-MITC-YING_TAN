package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务办理图片表
 * @author Mr.Deng
 * @date 2018/12/5 13:52
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("business_handling_img")
public class BusinessHandlingImg extends BaseEntity {
    /**
     * 业务办理表id
     */
    @TableField("business_handling_id")
    private Integer businessHandlingId;
    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;
}
