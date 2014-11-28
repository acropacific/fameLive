package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.constant.CommonConstants
import com.famelive.common.dto.usermanagement.UsersDto

class AdminCheckSlotAvailabilityDto extends AdminResponseDto {

    List<Long> duration
    String dateFormat

    AdminCheckSlotAvailabilityDto() {}

    AdminCheckSlotAvailabilityDto(AdminFetchGenreDto adminFetchGenreDto, UsersDto usersDto) {
        this.duration = CommonConstants.EVENT_SLOT_DURATIONS as List<Long>
        this.dateFormat = CommonConstants.INPUT_DATE_FORMAT
    }

    static AdminCheckSlotAvailabilityDto createAdminResponseDto(AdminFetchGenreDto adminFetchGenreDto, UsersDto usersDto) {
        return new AdminCheckSlotAvailabilityDto(adminFetchGenreDto, usersDto)
    }

}
