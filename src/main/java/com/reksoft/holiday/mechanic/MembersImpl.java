package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class MembersImpl implements MembersInterface {

    private Calculate calculate;
    private PlayersInterface playersPool;
    private static final Logger log = Logger.getLogger(MembersImpl.class);

    public MembersImpl(Calculate calculate,PlayersInterface playersPool) {
        this.calculate = calculate;
        this.playersPool = playersPool;
    }

    @Override
    public Calculate getCalculate() {
        return calculate;
    }

    @Override
    public void addMember(Player player, Instant inputTime){
        Member member = new Member(player, calculate,inputTime,false);
        calculate.getMemberSet().add(member);
        playersPool.setPlayerIsBusy(member.getPlayer());
    }
    @Override
    public void addMemberAsOrganizator(Player player, Instant inputTime){
        Member member = new Member(player, calculate,inputTime,true);
        playersPool.setPlayerIsOrganizer(member.getPlayer(),true);
        calculate.getMemberSet().add(member);
    }
    @Override
    public Set<Member> getAll() {
        return calculate.getMemberSet();
    }

    @Override
    public Set<Member> getOrganizators(){
        Set<Member> org = new HashSet<>();
        for (Member item:calculate.getMemberSet()
             ) {
            if (item.getIsOrganizator()){
                org.add(item);
            }
        }
        return org;
    }
    @Override
    public Set<Member> getAllWithoutOrganizators(){
        Set<Member> org = new HashSet<>();
        for (Member item:calculate.getMemberSet()
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
            Double points =  duration * calculate.getHoliday().getPointsRate();
            return points.intValue();
        }
        log.warn("getPointsForPeriod: member time is invalid");
       return 0;
    }
    @Override
    public boolean findMember(Member member){
        if (calculate.getMemberSet().contains(member)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean findPlayer(Player player){
        for (Member member: calculate.getMemberSet()
             ) {
            if (member.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setMemberSet(Set<Member> memberSet) {
       calculate.setMemberSet(memberSet);
    }

    @Override
    public Set<Member> getMemberSet() {
        return calculate.getMemberSet();
    }

    @Override
    public Set<Member> getActiveMembers() {
        Set<Member> memberSet = calculate.getMemberSet();
        for (Member item: memberSet
             ) {
            if (item.getOutputTime()==null){
                memberSet.add(item);
            }
        }
        return memberSet;
    }
}
