package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarPerception {
    List<Current> sr;
    List<Current> sc;
    List<Current> bxq;
    List<Current> wl;
}
