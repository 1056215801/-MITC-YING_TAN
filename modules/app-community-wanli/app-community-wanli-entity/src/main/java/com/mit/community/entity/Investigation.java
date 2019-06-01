package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/31 18:57
 * @Company mitesofor
 * @Description:民意调查
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("investigation")
public class Investigation extends BaseEntity {

    private String title;

    @TableField("community_code")
    private String communityCode;

    private Integer type;

    private Integer status;

    private Integer creater;

    private LocalDateTime begintime;

    private LocalDateTime endtime;

    private Integer visitorNum;
}
