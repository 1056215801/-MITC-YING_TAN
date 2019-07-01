package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author HuShanLin
 * @Date Created in 14:25 2019/6/29
 * @Company: mitesofor </p>
 * @Description:~
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfo {

    private String accountType;

    private String userName;

    private String adminName;

    private String cityName;

    private String areaName;

    private String streetName;

    private List<Community> communities = new ArrayList<>();
}
