package com.famelive.common.slotManagement

import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.util.DateTimeUtil
import com.famelive.common.util.slotmanagement.SlotManagementUtil

/**
 * Created by anil on 6/11/14.
 */
class Slot {

    int startBuffer
    //startBuffer should be in minutes

    int endBuffer
    //endBuffer should be in minutes

    int eventGap
    //endBuffer should be in minutes

    Date startTime
    Date endTime
    private boolean _isDefaultValuesSet = false

    private void _setDefaultValues() {
        if (this instanceof ChannelSlot) {
            //Do nothing, buffer and gap already included in slot
        } else {
            if (!_isDefaultValuesSet) {
                this.startBuffer = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_START_BUFFER)
                this.endBuffer = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_END_BUFFER)
                this.eventGap = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_GAP_TIME)
                _isDefaultValuesSet = true
            }
        }
    }

    public void setStartTime(Date startTime) {
        _setDefaultValues()
        Date eventStartTime = DateTimeUtil.roundOffMinuteInDate(startTime)
        this.startTime = DateTimeUtil.subtractMinutesFromDate(eventStartTime, (this.eventGap + this.startBuffer))
    }

    public void setEndTime(Date endTime) {
        _setDefaultValues()
        Date eventEndTime = DateTimeUtil.roundOffMinuteInDate(endTime)
        this.endTime = DateTimeUtil.addMinutesToDate(eventEndTime, this.endBuffer)
    }
}
