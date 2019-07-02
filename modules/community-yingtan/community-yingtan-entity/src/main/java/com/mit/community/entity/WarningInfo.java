package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarningInfo {
    private int isWarning;
    private String labels;
    private String warningInfo;
}
