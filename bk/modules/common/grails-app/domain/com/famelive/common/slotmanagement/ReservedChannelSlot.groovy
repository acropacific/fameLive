package com.famelive.common.slotmanagement

import com.famelive.common.streamManagement.WowzaChannel

//this slot is not actually booked but kept reserved for priority performers
class ReservedChannelSlot {
    WowzaChannel channel
    Date startTime
    Date endTime

    static constraints = {
        channel nullable: false
        startTime nullable: false
        endTime nullable: false
    }
}
