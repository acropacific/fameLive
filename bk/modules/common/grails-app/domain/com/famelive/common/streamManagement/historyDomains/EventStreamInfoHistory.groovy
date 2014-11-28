package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.WowzaChannel

class EventStreamInfoHistory {

    static constraints = {
        event unique: true
        streamName nullable: true
    }
    Event event
    WowzaChannel wowzaChannel
    String streamName

    public void setEvent(Event event) {
        this.event = event
        this.streamName = event.eventId
    }
}
