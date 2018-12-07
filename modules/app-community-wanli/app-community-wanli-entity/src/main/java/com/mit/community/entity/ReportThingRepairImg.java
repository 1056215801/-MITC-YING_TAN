package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报事报修图片表
 * @author Mr.Deng
 * @date 2018/12/3 19:40
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("report_thing_repair_img")
public class ReportThingRepairImg extends BaseEntity {
    /**
     * 报事报修表id
     */
    @TableField("report_thing_repair_id")
    private Integer reportThingRepairId;
    /**
     * 保修图片地址
     */
    @TableField("img_url")
    private String imgUrl;
}
