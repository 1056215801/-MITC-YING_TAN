package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 快递信息表
 * @author Mr.Deng
 * @date 2018/12/14 16:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("express_info")
public class ExpressInfo extends BaseEntity {
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
}
