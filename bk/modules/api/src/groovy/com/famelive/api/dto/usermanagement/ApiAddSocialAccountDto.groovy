package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.SocialAccountDto

@APIResponseClass
class ApiAddSocialAccountDto extends ApiResponseDto {

    ApiAddSocialAccountDto() {
    }

    ApiAddSocialAccountDto(SocialAccountDto addSocialAccountDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.ADD_SOCIAL_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiAddSocialAccountDto createApiResponseDto(SocialAccountDto addSocialAccountDto) {
        return new ApiAddSocialAccountDto(addSocialAccountDto)
    }
}
