package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.SocialAccountDto

@APIResponseClass
class ApiRemoveSocialAccountDto extends ApiResponseDto {

    ApiRemoveSocialAccountDto() {
    }

    ApiRemoveSocialAccountDto(SocialAccountDto addSocialAccountDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.REMOVE_SOCIAL_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiRemoveSocialAccountDto createApiResponseDto(SocialAccountDto addSocialAccountDto) {
        return new ApiRemoveSocialAccountDto(addSocialAccountDto)
    }
}
