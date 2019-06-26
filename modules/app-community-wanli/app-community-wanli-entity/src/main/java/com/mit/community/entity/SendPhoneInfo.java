package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author HuShanLin
 * @Date Created in 9:33 2019/6/25
 * @Company: mitesofor </p>
 * @Description:~
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("send_phone")
public class SendPhoneInfo extends BaseEntity{
    private int dj;//号码的等级
    private String phone;
}
