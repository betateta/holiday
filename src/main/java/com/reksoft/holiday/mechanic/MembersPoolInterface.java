package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.Player;

public interface MembersPoolInterface {
    Member createMember(Player player, Calculate calc);


}
