package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.VerifyUserEmailDto

@APIResponseClass
class ApiVerifyUserEmailDto extends ApiResponseDto {

    ApiVerifyUserEmailDto() {}

    ApiVerifyUserEmailDto(VerifyUserEmailDto verifyUserEmailDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CHECK_USER_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiVerifyUserEmailDto createApiResponseDto(VerifyUserEmailDto verifyUserEmailDto) {
        return new ApiVerifyUserEmailDto(verifyUserEmailDto)

    }

}
