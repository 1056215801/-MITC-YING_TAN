package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DevicePerception {
    Map<String,String> yg;
    Map<String,String> dc;
    Map<String,String> jg;
    Map<String,String> mj;
    Map<String,String> sxj;
}
