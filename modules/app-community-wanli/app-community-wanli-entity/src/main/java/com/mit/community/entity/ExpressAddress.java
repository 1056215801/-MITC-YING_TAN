package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 快递位置信息表
 * @author Mr.Deng
 * @date 2018/12/14 16:29
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("express_address")
public class ExpressAddress extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 快递商名
     */
    private String name;
    /**
     * 领取位置
     */
    private String address;
    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 创建人姓名
     */
    @TableField("create_user_name")
    private String createUserName;
    /**
     * 读取状态
     */
    @TableField(exist = false)
    private Boolean readStatus;
    /**
     * 未领取快递数
     */
    @TableField(exist = false)
    private Integer expressNum;
    /**
     * 总数量
     */
    @TableField(exist = false)
    private Integer total;
}
