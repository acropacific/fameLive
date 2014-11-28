package com.famelive.common.enums.slotmanagement

public enum EventStatus {
    UP_COMING("Up Coming"),
    READY("Ready"),
    PAUSED("paused"),
    ON_AIR("On Air"),
    CANCELED("Canceled"),
    COMPLETED("Completed")

    String value;

    EventStatus(String value) {
        this.value = value;
    }
}