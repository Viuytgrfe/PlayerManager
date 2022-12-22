package me.vineer.playermanager;

import org.bukkit.Location;

public class DonateHome {
    private final Location location;
    private final String member;
    private final int slot;
    private final String home_name;

    public Location getLocation() {
        return location;
    }

    public String getMember() {
        return member;
    }

    public int getSlot() {
        return slot;
    }

    public String getHome_name() {
        return home_name;
    }

    public DonateHome(Location location, String member, int slot, String home_name) {
        this.location = location;
        this.member = member;
        this.slot = slot;
        this.home_name = home_name;
    }
}