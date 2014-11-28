package com.famelive.common.slotmanagement

import com.famelive.common.streamManagement.WowzaChannel

class BookedChannelSlotHistory {
    Event event
    WowzaChannel channel

    Date startTime
    Date endTime

    boolean isActive = true


    static constraints = {
        event nullable: true
    }
}
