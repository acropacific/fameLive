package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserProfileDto

@APIResponseClass
class ApiUserProfileDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String username
    @APIResponseField(include = true)
    public String email
    @APIResponseField(include = true)
    public String fameName
    @APIResponseField(include = true)
    public String mobile
    @APIResponseField(include = true)
    public String imageName
    @APIResponseField(include = true)
    public String imageFolder
    Date dateCreated

    ApiUserProfileDto() {}

    ApiUserProfileDto(UserProfileDto userProfileDto) {
        this.username = userProfileDto.username
        this.fameName = userProfileDto.fameName ?: ""
        this.email = userProfileDto.email
        this.mobile = userProfileDto?.mobile ?: ''
        this.imageName = userProfileDto?.imageName ?: ''
        this.dateCreated = userProfileDto?.dateCreated
        this.imageFolder = userProfileDto?.imageFolder
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.UPDATE_PROFILE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiUserProfileDto createApiResponseDto(UserProfileDto userProfileDto) {
        return new ApiUserProfileDto(userProfileDto)
    }
}
