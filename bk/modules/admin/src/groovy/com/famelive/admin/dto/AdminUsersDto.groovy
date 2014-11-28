package com.famelive.admin.dto

import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.dto.usermanagement.UsersDto

class AdminUsersDto {
    List<AdminUserProfileDto> profileDtoList
    Integer userCount

    AdminUsersDto() {}

    AdminUsersDto(UsersDto usersDto) {
        this.profileDtoList = createUserProfileList(usersDto)
        this.userCount = usersDto.count
    }

    static AdminUsersDto createAdminResponseDto(UsersDto usersDto) {
        return new AdminUsersDto(usersDto)
    }

    static List<AdminUserProfileDto> createUserProfileList(UsersDto usersDto) {
        List<AdminUserProfileDto> profileDtoList = []
        usersDto.userList.each { UserProfileDto profileDto ->
            profileDtoList << AdminUserProfileDto.createAdminResponseDto(profileDto)
        }
        return profileDtoList
    }
}
