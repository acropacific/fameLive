package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class UsersDto extends ResponseDto {

    List<UserProfileDto> userList
    Integer count

    UsersDto() {}

    UsersDto(List<UserProfileDto> profileDtoList, Integer count) {
        this.userList = profileDtoList
        this.count = count
    }

    UsersDto(List<User> users) {
        this.userList = populateUserProfileDto(users)
    }

    static UsersDto createCommonResponseDto(List<UserProfileDto> profileDtoList, Integer count) {
        return new UsersDto(profileDtoList, count)
    }

    static UsersDto createCommonResponseDto(List<User> users) {
        return new UsersDto(users)
    }

    static List<UserProfileDto> populateUserProfileDto(List<User> users) {
        List<UserProfileDto> profileDtoList = []
        users?.each { User user ->
            profileDtoList << new UserProfileDto(user)
        }
        return profileDtoList
    }

}
