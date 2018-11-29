package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别。1、男。0、女。
     */
    private Short gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String cellphone;
    /**
     * 头像
     */
    private String icon_url;
    /**
     * 出生日期
     */
    private LocalDateTime birthday;
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
