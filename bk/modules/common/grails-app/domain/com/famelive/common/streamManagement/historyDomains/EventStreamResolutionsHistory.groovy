package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.slotmanagement.Event

class EventStreamResolutionsHistory {
    Event event

    static constraints = {
        event nullable: false
        resolutions nullable: false
    }

    static hasMany = [resolutions: Integer]
}
