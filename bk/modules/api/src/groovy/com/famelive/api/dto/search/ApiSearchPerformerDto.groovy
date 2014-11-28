package com.famelive.api.dto.search

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.dto.usermanagement.ApiSearchPerformerProfileDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.dto.usermanagement.UsersDto

@APIResponseClass
class ApiSearchPerformerDto extends ApiResponseDto {

    @APIResponseField(include = true, key = "performers")
    public List<ApiSearchPerformerProfileDto> performers
    @APIResponseField(include = true, key = "totalUsers")
    public Integer userCount

    ApiSearchPerformerDto() {
    }

    ApiSearchPerformerDto(UsersDto usersDto) {
        this.performers = createUserProfileList(usersDto)
        this.userCount = usersDto?.count
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.SEARCH_PERFORMER.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiSearchPerformerDto createApiResponseDto(UsersDto usersDto) {
        return new ApiSearchPerformerDto(usersDto)
    }

    static List<ApiSearchPerformerProfileDto> createUserProfileList(UsersDto usersDto) {
        List<ApiSearchPerformerProfileDto> profileDtoList = []
        usersDto.userList.each { UserProfileDto profileDto ->
            profileDtoList << ApiSearchPerformerProfileDto.createApiResponseDto(profileDto)
        }
        return profileDtoList
    }
}
