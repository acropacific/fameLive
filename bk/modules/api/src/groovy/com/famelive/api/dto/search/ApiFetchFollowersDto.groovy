package com.famelive.api.dto.search

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.dto.usermanagement.ApiUserProfileDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.dto.usermanagement.UsersDto

@APIResponseClass
class ApiFetchFollowersDto extends ApiResponseDto {

    @APIResponseField(include = true, key = "followers")
    public List<ApiUserProfileDto> performers

    ApiFetchFollowersDto() {
    }

    ApiFetchFollowersDto(UsersDto usersDto) {
        this.performers = createUserProfileList(usersDto)
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FETCH_FOLLOWERS.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchFollowersDto createApiResponseDto(UsersDto usersDto) {
        return new ApiFetchFollowersDto(usersDto)
    }

    static List<ApiUserProfileDto> createUserProfileList(UsersDto usersDto) {
        List<ApiUserProfileDto> profileDtoList = []
        usersDto.userList.each { UserProfileDto profileDto ->
            profileDtoList << ApiUserProfileDto.createApiResponseDto(profileDto)
        }
        return profileDtoList
    }
}
