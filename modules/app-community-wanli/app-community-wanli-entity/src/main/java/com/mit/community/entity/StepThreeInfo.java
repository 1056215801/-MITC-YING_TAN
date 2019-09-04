package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StepThreeInfo {
    private HouseHold houseHold;
    private List<AccessCard> cardList;
    private List<HouseHoldPhoto> photoList;
    private List<AuthorizeGroup> authList;
}
