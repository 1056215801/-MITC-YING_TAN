package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帮助
 * @author shuyy
 * @date 2019-01-03
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("help")
public class Help extends BaseEntity{

    private String title;

    private String content;

    private Short orders;

}
