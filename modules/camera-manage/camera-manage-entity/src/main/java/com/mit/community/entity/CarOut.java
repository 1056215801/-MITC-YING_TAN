package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarOut {
    private String CPH;
    private String InTime;
    private String InGateName;
    private String InPic;
    private String SFGate;
    private String CarparkNO;
    private String OutTime;
    private String OutGateName;
    private String OutPic;
    private String SFTime;
    private double SFJE;
    private double YSJE;
    private String SFGate_Out;
}
