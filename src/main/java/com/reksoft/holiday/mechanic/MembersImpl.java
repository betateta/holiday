package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MembersImpl implements MembersInterface {

    private Calculate calculate;

    public MembersImpl(Calculate calculate) {
        this.calculate = calculate;
    }

    @Override
    public List<Member> getMemberList() {
        return calculate.getMemberList();
    }

    @Override
    public Calculate getCalculate() {
        return calculate;
    }

    @Override
    public void setMemberList(List<Member> memberList){
        this.calculate.setMemberList(memberList);
    }

    @Override
    public void addMember(Player player, Instant inputTime){
        calculate.getMemberList().add(new Member(player, calculate, inputTime, false));
    }
    @Override
    public void addMemberAsOrganizator(Player player, Instant inputTime){
        calculate.getMemberList().add(new Member(player, calculate,inputTime,true));
    }
    @Override
    public List<Member> getAll() {
        return calculate.getMemberList();
    }

    @Override
    public List<Member> getOrganizators(){
        List<Member> org = new ArrayList<>();
        for (Member item:calculate.getMemberList()
             ) {
            if (item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    }
    @Override
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
    @Override
    public Integer getPointsForPeriod(Member member){
        /*duration of last period*/
        if (member.getOutputTime().isAfter(member.getInputTime())){
            long duration = member.getOutputTime().getEpochSecond() - member.getInputTime().getEpochSecond();
        /* points of last period*/
            int points = ((int) duration) * calculate.getHoliday().getPointsRate().intValue();
            return points;
        }
       return 0;
    }
    @Override
    public boolean findMember(Member member){
        if (calculate.getMemberList().contains(member)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean findPlayer(Player player){
        for (Member member: calculate.getMemberList()
             ) {
            if (member.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
