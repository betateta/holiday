package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;

import java.time.Instant;
import java.util.List;

public interface MembersInterface {
    void setMemberList(List<Member> memberList);

    void addMember(Player player, Instant inputTime);

    void addMemberAsOrganizator(Player player, Instant inputTime);

    List<Member> getAll();

    List<Member> getOrganizators();

    List<Member> getAllWithoutOrganizators();

    Integer getPointsForPeriod(Member member);

    boolean findMember(Member member);

    boolean findPlayer(Player player);

    List<Member> getMemberList();

    com.reksoft.holiday.model.Calculate getCalculate();
}
