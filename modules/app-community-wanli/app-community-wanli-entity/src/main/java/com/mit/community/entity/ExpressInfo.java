package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 快递信息表
 *
 * @author Mr.Deng
 * @date 2018/12/14 16:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("express_info")
public class ExpressInfo extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 关联user用户表id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 关联express_address快递位置表id
     */
    @TableField("express_address_id")
    private Integer expressAddressId;
    /**
     * 运单编号
     */
    @TableField("waybill_num")
    private String waybillNum;
    /**
     * 领取状态1、已领取2、未领取
     */
    @TableField("receive_status")
    private Integer receiveStatus;
    /**
     * 领取时间
     */
    @TableField("receive_time")
    private LocalDateTime receiveTime;
    /**
     * 领取人
     */
    private String receiver;
    /**
     * 领取人电话
     */
    @TableField("receiver_phone")
    private String receiverPhone;
    /**
     * 创建人
     */
    @TableField("create_user_name")
    private String createUserName;
    /**
     * 领取地址
     */
    @TableField(exist = false)
    private String address;
    /**
     * 联系人
     */
    private String linker;
    /**
     * 联系电话
     */
    private String linkphone;
}
