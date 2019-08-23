package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author qishengjun
 * @Date Created in 21:56 2019/8/22
 * @Company: mitesofor </p>
 * @Description:~
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomVo {
    private Integer id;
    private String roomNum;
    private String zoneName;
    private String buildingName;
    private String unitName;
    private Integer roomStatus;
}
