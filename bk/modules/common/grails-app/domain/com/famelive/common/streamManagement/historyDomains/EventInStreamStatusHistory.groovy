package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.slotmanagement.Event

class EventInStreamStatusHistory {

    static constraints = {
    }

    Event event
    String status
    String clientIP
}
