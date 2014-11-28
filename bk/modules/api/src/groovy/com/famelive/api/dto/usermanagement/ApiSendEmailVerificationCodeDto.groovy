package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.SendEmailVerificationCodeDto
import com.famelive.common.dto.usermanagement.VerifyUserEmailDto

@APIResponseClass
class ApiSendEmailVerificationCodeDto extends ApiResponseDto {

    ApiSendEmailVerificationCodeDto() {}

    ApiSendEmailVerificationCodeDto(SendEmailVerificationCodeDto sendEmailVerificationCodeDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.SEND_EMAIL_VERIFICATION_CODE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiSendEmailVerificationCodeDto createApiResponseDto(SendEmailVerificationCodeDto sendEmailVerificationCodeDto) {
        return new ApiSendEmailVerificationCodeDto(sendEmailVerificationCodeDto)

    }

}
