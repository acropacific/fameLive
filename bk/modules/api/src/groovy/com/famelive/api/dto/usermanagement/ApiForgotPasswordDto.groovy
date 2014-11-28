package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.ForgotPasswordDto

@APIResponseClass
class ApiForgotPasswordDto extends ApiResponseDto {

    ApiForgotPasswordDto() {
        this.code = APIActions.FORGOT_PASSWORD.successCode
        String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.message = msg ? msg : ""
    }

    ApiForgotPasswordDto(ForgotPasswordDto forgotPasswordDto) {
        this.code = APIActions.FORGOT_PASSWORD.successCode
        String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.message = msg ? msg : ""
    }

    static ApiForgotPasswordDto createApiResponseDto(ForgotPasswordDto forgotPasswordDto) {
        return new ApiForgotPasswordDto(forgotPasswordDto)
    }

//    @APIResponseField(include = true)
    public String email
}
