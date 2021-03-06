package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
     * 住户id
     */
    @TableField("household_id")
    private Integer householdId;
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
    /**
     * 小区身份
     */
    private String role;
    /**
     * 地区
     */
    private String region;

    @TableField(exist = false)
    private List<HouseholdRoom> householdRoomList;

    /**
     * 是否有密码
     */
    @TableField(exist = false)
    private Boolean havePassword;

    /**
     * 业主状态：0-注销；1-启用；2-停用；3-权限过期
     */
    @TableField(exist = false)
    private Integer househouldStatus;

    /**
     * 个体编号
     */
    private String serialnumber;

    /**
     * 原始照片Base64
     */
    @TableField("photo_base64")
    private String photoBase64;

    /**
     * 原始照片地址
     */
    @TableField("photo_url")
    private String photourl;

    /**
     * 模拟门禁卡号
     */
    private String doornum;

    /**
     * 卡号
     */
    private String cardnum;

    /**
     * 百度人脸库id
     */
    @TableField("face_token")
    private String faceToken;

    /**
     * 是否将数据同步
     */
    @TableField("is_synchro")
    private Integer isSync;

    /**
     * 身份类型
     */
    @TableField("identity_type")
    private Integer identity;

    /**
     * 指纹信息
     */
    private String fingerprint;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    @TableField("id_card_num")
    private String idCardNumber;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 星座
     */
    private String constellation;

}
