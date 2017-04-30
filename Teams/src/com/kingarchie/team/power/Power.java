package com.kingarchie.team.power;

import com.kingarchie.team.Faction;

import java.util.UUID;

public class Power {

    private UUID teamID;

    public Power(Faction team) {
        this.teamID = team.getTeamID();
    }
}
