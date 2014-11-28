package com.famelive.common.slotmanagement

import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.util.slotmanagement.SlotManagementUtil

class BookedChannelSlot {
    WowzaChannel channel
    boolean isActive = true
    int startBuffer
    int endBuffer
    int eventGap
    Date startTime
    Date endTime

    static constraints = {
    }
    private boolean _isDefaultValuesSet = false

    private void _setDefaultValues() {
        if (!_isDefaultValuesSet) {
            startBuffer = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_START_BUFFER)
            endBuffer = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_END_BUFFER)
            eventGap = SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_GAP_TIME)
            _isDefaultValuesSet = true
        }
    }

    public void setStartTime(Date startTime) {
        _setDefaultValues()
        this.startTime = startTime
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime
    }
}
