package com.reksoft.holiday.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcMembers {
    private Calculate calculate;
    private List<Member> memberList;

}
