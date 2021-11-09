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
        this.calculate.setMemberList(memberList);
    }
    public void addMember (Player player,Instant inputTime){
        calculate.getMemberList().add(new Member(player, calculate, inputTime, false));
    }
    public void addMemberAsOrganizator (Player player, Instant inputTime){
        calculate.getMemberList().add(new Member(player, calculate,inputTime,true));
    }
    public List<Member> getAll()
    {
        return calculate.getMemberList();

    };
    public List<Member> getOrganizators(){
        List<Member> org = new ArrayList<>();
        for (Member item:calculate.getMemberList()
             ) {
            if (item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    };
    public List<Member> getAllWithoutOrganizators(){
        List<Member> org = new ArrayList<>();
        for (Member item:calculate.getMemberList()
        ) {
            if (!item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    }
    public Integer getPointsForPeriod (Member member){
        /*duration of last period*/
        if (member.getOutputTime().isAfter(member.getInputTime())){
            long duration = member.getOutputTime().getEpochSecond() - member.getInputTime().getEpochSecond();
        /* points of last period*/
            int points = ((int) duration) * calculate.getHoliday().getPointsRate().intValue();
            return points;
        }
       return 0;
    }
    public boolean findMember (Member member){
        if (calculate.getMemberList().contains(member)) {
            return true;
        }
        return false;
    }
    public boolean findPlayer(Player player){
        for (Member member: calculate.getMemberList()
             ) {
            if (member.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public MembersPool(Calculate calculate) {
        this.calculate = calculate;
        this.memberList = calculate.getMemberList();

    }
}
