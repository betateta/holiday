package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MembersPool {

    private List<Member> memberList;
    private Calculate calculate;

    public void setMemberList (List<Member> memberList){
        this.memberList=memberList;
    }
    public void addMember (Player player,Instant inputTime){
        memberList.add(new Member(player, calculate, inputTime, false));
    }
    public void addMemberAsOrganizator (Player player, Instant inputTime){
        memberList.add(new Member(player, calculate,inputTime,true));
    }
    public List<Member> getAll(){
        return memberList;
    };
    public List<Member> getOrganizators(){
        List<Member> org = new ArrayList<>();
        for (Member item:memberList
             ) {
            if (item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    };
    public List<Member> getAllWithoutOrganizators(){
        List<Member> org = new ArrayList<>();
        for (Member item:memberList
        ) {
            if (!item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    };

    public MembersPool(Calculate calculate, List<Member> memberList) {
        this.calculate = calculate;
        this.memberList = memberList;

    }
}
