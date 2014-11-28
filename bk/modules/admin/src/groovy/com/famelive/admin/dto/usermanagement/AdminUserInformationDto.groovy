package com.famelive.admin.dto.usermanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.admin.dto.slotmanagement.AdminFetchEventsDto
import com.famelive.common.dto.slotmanagement.FetchEventsDto
import com.famelive.common.dto.usermanagement.UserProfileDto

class AdminUserInformationDto extends AdminResponseDto {
    AdminUserProfileDto adminUserProfileDto
    AdminFetchEventsDto adminFetchEventsDto

    AdminUserInformationDto() {}

    AdminUserInformationDto(UserProfileDto profileDto, FetchEventsDto fetchEventsDto) {
        this.adminUserProfileDto = AdminUserProfileDto.createAdminResponseDto(profileDto)
        this.adminFetchEventsDto = AdminFetchEventsDto.createAdminResponseDto(fetchEventsDto)
    }

    static AdminUserInformationDto createAdminResponseDto(UserProfileDto profileDto, FetchEventsDto fetchEventsDto) {
        return new AdminUserInformationDto(profileDto, fetchEventsDto)
    }
}
