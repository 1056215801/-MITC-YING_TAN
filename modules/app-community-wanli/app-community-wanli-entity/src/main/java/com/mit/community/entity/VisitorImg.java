package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 访客抓拍图片
 * @author Mr.Deng
 * @date 2018/12/3 17:06
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("visitor_img")
public class VisitorImg extends BaseEntity {
    /**
     * 访客信息表id
     */
    @TableField("visitor_id")
    private Integer visitorId;
    /**
     * 抓拍图片地址
     */
    @TableField("img_url")
    private String imgUrl;
}
