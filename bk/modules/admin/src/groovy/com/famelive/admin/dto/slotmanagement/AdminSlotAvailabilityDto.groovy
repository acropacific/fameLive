package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.constant.CommonConstants

class AdminSlotAvailabilityDto extends AdminResponseDto {

    List<Long> duration
    String dateFormat

    AdminSlotAvailabilityDto() {
        this.duration = CommonConstants.EVENT_SLOT_DURATIONS as List<Long>
        this.dateFormat = CommonConstants.INPUT_DATE_FORMAT
    }

    static AdminSlotAvailabilityDto createAdminResponseDto() {
        return new AdminSlotAvailabilityDto()
    }

}
