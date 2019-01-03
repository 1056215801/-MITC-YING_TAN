package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 快递提醒已读表
 * @author Mr.Deng
 * @date 2018/12/14 16:46
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("express_read_user")
public class ExpressReadUser extends BaseEntity {
    /**
     * 关联user表，用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 关联express_info快递表id
     */
    @TableField("express_info_id")
    private Integer expressInfoId;
    /**
     * 关联express_address快递位置表id
     */
    @TableField("express_address_id")
    private Integer expressAddressId;
}
