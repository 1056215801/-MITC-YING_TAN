package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarPerception {
    Map<String,String> sr;
    Map<String,String> sc;
    Map<String,String> bxq;
    Map<String,String> wl;
}
