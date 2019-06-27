package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CameraLb {
    private String indexCode;
    private String name;
    private String parentIndexCode;
    private String treeCode;
}
