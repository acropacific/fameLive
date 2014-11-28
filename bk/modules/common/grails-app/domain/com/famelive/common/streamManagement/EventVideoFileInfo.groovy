package com.famelive.common.streamManagement

import com.famelive.common.slotmanagement.Event
import com.famelive.common.slotmanagement.EventHistory

class EventVideoFileInfo {

    static constraints = {
        historyEvent nullable: true, validator: { val, obj ->
            if (val == null && obj.event == null) throw new RuntimeException("Both event and historyEvent can't be null at same time")
        }
        event nullable: true, validator: { val, obj ->
            if (val == null && obj.historyEvent == null) throw new RuntimeException("Both event and historyEvent can't be null at same time")
        }
    }

    Event event
    EventHistory historyEvent
    String serverIP
    String parentDirectory
    String fileName
    int videoResolution
}
