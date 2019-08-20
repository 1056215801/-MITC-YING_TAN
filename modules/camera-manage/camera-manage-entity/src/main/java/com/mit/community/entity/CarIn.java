package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarIn{
    private String CPH;
    private String InTime;
    private String InGateName;
    private String InPic;
    private String SFGate;
    private String CarparkNO;
}
