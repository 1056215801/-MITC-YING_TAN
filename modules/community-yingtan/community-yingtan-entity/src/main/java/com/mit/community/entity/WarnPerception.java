package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarnPerception {
    List<Current> dc;
    List<Current> xf;
    List<Current> tx;
    List<Current> mwg;
}
