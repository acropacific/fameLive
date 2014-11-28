package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.admin.dto.AdminUsersDto
import com.famelive.common.constant.CommonConstants
import com.famelive.common.dto.usermanagement.UsersDto

class AdminCreateEventDto extends AdminResponseDto {

    AdminFetchGenreDto adminFetchGenreDto
    AdminUsersDto userDetails
    List<Long> duration
    String dateFormat

    AdminCreateEventDto() {}

    AdminCreateEventDto(AdminFetchGenreDto adminFetchGenreDto, UsersDto usersDto) {
        this.adminFetchGenreDto = adminFetchGenreDto
        this.duration = CommonConstants.EVENT_SLOT_DURATIONS as List<Long>
        this.dateFormat = CommonConstants.INPUT_DATE_FORMAT
        this.userDetails = new AdminUsersDto(usersDto)
    }

    static AdminCreateEventDto createAdminResponseDto(AdminFetchGenreDto adminFetchGenreDto, UsersDto usersDto) {
        return new AdminCreateEventDto(adminFetchGenreDto, usersDto)
    }

}
