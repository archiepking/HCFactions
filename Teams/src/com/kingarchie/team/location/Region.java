package com.kingarchie.team.location;

import com.kingarchie.team.Faction;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Region {

    private static Set<Region> regions = new HashSet<>();
    private UUID teamID;
    private Location2D[] region;
    public Region(Faction team, String world, int x1, int z1, int x2, int z2) {
        this.regions.add(this);
        this.teamID = team.getTeamID();
        this.region = new Location2D[] {
                new Location2D(world, x1, z1),
                new Location2D(world, x2, z2)
        };
    }

    public boolean isInside(Location2D location) {
        int minX = Math.min(region[0].getX(), region[1].getX());
        int maxX = Math.max(region[0].getX(), region[1].getX());
        int minZ = Math.min(region[0].getZ(), region[1].getZ());
        int maxZ = Math.max(region[0].getZ(), region[1].getZ());
        return (location.getWorld().equalsIgnoreCase(region[0].getWorld()) && (location.getX() > minX && location.getX() < maxX) && (location.getZ() > minZ && location.getZ() < maxZ));
    }

    public static Set<Region> getRegions() {
        return regions;
    }

    public UUID getTeamID() {
        return teamID;
    }

    public Location2D[] getRegion() {
        return region;
    }

    public Location2D getFirstRegion() {
        return region[0];
    }

    public Location2D getSecondRegion() {
        return region[1];
    }
}
