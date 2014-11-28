package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.ChangePasswordDto

@APIResponseClass
class ApiChangePasswordDto extends ApiResponseDto {


    ApiChangePasswordDto() {}

    ApiChangePasswordDto(ChangePasswordDto commonRegistrationDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CHANGE_PASSWORD.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiChangePasswordDto createApiResponseDto(ChangePasswordDto registrationDto) {
        return new ApiChangePasswordDto(registrationDto)
    }

}
