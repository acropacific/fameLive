package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.ChangeEmailDto
import com.famelive.common.dto.usermanagement.ChangePasswordDto

@APIResponseClass
class ApiChangeEmailDto extends ApiResponseDto {


    ApiChangeEmailDto() {}

    ApiChangeEmailDto(ChangeEmailDto changeEmailDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CHANGE_EMAIL.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiChangeEmailDto createApiResponseDto(ChangeEmailDto changeEmailDto) {
        return new ApiChangeEmailDto(changeEmailDto)

    }

}
