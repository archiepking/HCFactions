package com.kingarchie.team.location;

import org.bukkit.Location;

public class Location2D {

    private int[] coordinates;
    private String world;

    public Location2D(String world, int x, int z) {
        setCoordinates(x, z);
        setWorld(world);
    }

    public Location2D(Location location) {
        this(location.getWorld().getName(), location.getBlockX(), location.getBlockZ());
    }

    public int getX() {
        return coordinates[0];
    }

    public void setX(int x) {
        this.coordinates[0] = x;
    }

    public int getZ() {
        return coordinates[1];
    }

    public void setZ(int z) {
        this.coordinates[1] = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String worldName) {
        this.world = worldName;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int x, int z) {
        this.coordinates[0] = x;
        this.coordinates[1] = z;
    }
}
