package com.famelive.common.streamManagement

import com.famelive.common.slotmanagement.Event

class EventStreamResolutions {
    Event event

    static constraints = {
        event nullable: false
        resolutions nullable: false
    }

    static hasMany = [resolutions: Integer]
}
