package com.famelive.common.slotManagement

import com.famelive.common.streamManagement.WowzaChannel

/**
 * Created by anil on 6/11/14.
 */
class ChannelSlot extends Slot {
    WowzaChannel channel
    boolean isAvailable
    boolean isReserved
    boolean isBooked
}
