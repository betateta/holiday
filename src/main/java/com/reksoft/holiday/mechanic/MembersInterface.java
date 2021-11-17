package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;

import java.time.Instant;
import java.util.Set;

public interface MembersInterface {
    void setMemberSet(Set<Member> memberSet);

    void addMember(Player player, Instant inputTime);

    void addMemberAsOrganizator(Player player, Instant inputTime);

    Set<Member> getAll();

    Set<Member> getOrganizators();

    Set<Member> getAllWithoutOrganizators();

    Integer getPointsForPeriod(Member member);

    boolean findMember(Member member);

    boolean findPlayer(Player player);

    Set<Member> getMemberSet();
    Set<Member> getActiveMembers();

    com.reksoft.holiday.model.Calculate getCalculate();
}
