package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UpdateForgotPasswordDto

@APIResponseClass
class ApiUpdateForgotPasswordDto extends ApiResponseDto {

    ApiUpdateForgotPasswordDto() {
    }

    ApiUpdateForgotPasswordDto(UpdateForgotPasswordDto updateForgotPasswordDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.UPDATE_FORGOT_PASSWORD.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiUpdateForgotPasswordDto createApiResponseDto(UpdateForgotPasswordDto updateForgotPasswordDto) {
        return new ApiUpdateForgotPasswordDto(updateForgotPasswordDto)
    }
}
