package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user")
public class User extends BaseEntity {

    /**
     * 手机号
     */
    private String cellphone;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别。0、未知。1、男。2、女。
     */
    private Short gender;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String icon_url;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    /**
     * 血型
     */
    @TableField("blood_type")
    private String bloodType;
    /**
     * 职业
     */
    private String profession;
    /**
     * 我的签名
     */
    private String signature;

}
