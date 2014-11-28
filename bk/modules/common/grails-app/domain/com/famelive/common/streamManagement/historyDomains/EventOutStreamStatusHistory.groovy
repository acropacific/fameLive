package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.slotmanagement.Event

class EventOutStreamStatusHistory {

    static constraints = {
    }

    Event event
    String status
    // TODO : have to store watcher's ip
}
