package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 申请钥匙图片
 * @author Mr.Deng
 * @date 2018/12/3 14:46
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName("apply_key_img")
@Data
public class ApplyKeyImg extends BaseEntity {
    /**
     * 钥匙申请id。关联apply_key表id
     */
    @TableField("apply_key_id")
    private Integer applyKeyId;
    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;

}
