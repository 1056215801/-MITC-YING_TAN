package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 失物招领内容详情表
 * @author Mr.Deng
 * @date 2018/12/17 20:26
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("lost_fount_content")
public class LostFountContent extends BaseEntity {
    /**
     * 关联lost_fount表id
     */
    @TableField("lost_fount_id")
    private Integer lostFountId;
    /**
     * 失物详情
     */
    private String content;
}
