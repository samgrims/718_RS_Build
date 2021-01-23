package com.rs.custom.data_structures;

public enum EntityDirection {
    NORTH(8192),
    SOUTH(0),
    EAST(12288),
    WEST(4096),
    NORTHEAST(10240),
    SOUTHEAST(14366),
    NORTHWEST(6144),
    SOUTHWEST(2048);

    private int value;

    public int getValue() {
        return value;
    }

    EntityDirection(int value) {
        this.value = value;
    }
}