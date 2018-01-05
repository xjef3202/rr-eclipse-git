package com.sysco.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemHistory {

    private int caseWeek1;
    private int caseWeek2;
    private int caseWeek3;
    private int caseWeek4;
    private int caseTotal;
    private int splitWeek1;
    private int splitWeek2;
    private int splitWeek3;
    private int splitWeek4;
    private int splitTotal;
    private String lastOrderedDate;

}
