package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息.
 *
 * @author Angel(QQ : 412887952)
 * @version v.0.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("web_sys_user")
public class SysUser extends BaseEntity {

    /**
     * 姓名
     */
    private String name;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码;
     */
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
     */
    private byte state;

    /**小区code*/
    @TableField("community_code")
    private String communityCode;
    /**
     * 电子邮件
     */
    private String email;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLogin;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;


}