package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemScheduleAndLzdInfo {
    private List<HandleProblemInfo> list;
    List<ReportProblemLzInfo> lzInfo;
}
