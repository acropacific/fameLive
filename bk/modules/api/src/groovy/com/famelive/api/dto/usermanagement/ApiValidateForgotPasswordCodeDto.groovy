package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.ValidateForgotPasswordCodeDto

@APIResponseClass
class ApiValidateForgotPasswordCodeDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String successToken

    ApiValidateForgotPasswordCodeDto() {}

    ApiValidateForgotPasswordCodeDto(ValidateForgotPasswordCodeDto forgotPasswordCodeDto) {
        this.successToken = forgotPasswordCodeDto?.successToken
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.VALIDATE_FORGOT_PASSWORD_CODE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiValidateForgotPasswordCodeDto createApiResponseDto(ValidateForgotPasswordCodeDto forgotPasswordCodeDto) {
        return new ApiValidateForgotPasswordCodeDto(forgotPasswordCodeDto)
    }
}
