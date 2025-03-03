package com.dynamischrijbaansysteem;

public enum LaneStatus {
    OPEN_EXTRA_LANE,
    CLOSE_EXTRA_LANE,
    CLOSED,
    UNKNOWN;

    public static LaneStatus fromString(String status) {
        if (status == null){
            return UNKNOWN;
        }
        try {
           return  LaneStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

