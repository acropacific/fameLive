package com.famelive.api.enums

import com.famelive.common.enums.slotmanagement.EventStatus

public enum ApiEventStatus {
    UP_COMING("Up Coming", EventStatus.UP_COMING, "upComing"),
    ON_AIR("On Air", EventStatus.ON_AIR, "onAir"),
    CANCELED("Canceled", EventStatus.CANCELED, "canceled"),
    COMPLETED("Canceled", EventStatus.COMPLETED, "completed")

    String value
    EventStatus eventStatus
    String key

    ApiEventStatus(String value, EventStatus eventStatus, String key) {
        this.value = value
        this.eventStatus = eventStatus
        this.key = key
    }
}