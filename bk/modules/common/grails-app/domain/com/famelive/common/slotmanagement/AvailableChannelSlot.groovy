package com.famelive.common.slotmanagement

import com.famelive.common.streamManagement.WowzaChannel

class AvailableChannelSlot {
    WowzaChannel channel
    Date startTime
    Date endTime

    static constraints = {
        channel nullable: false
        startTime nullable: false
        endTime nullable: false
    }
}
