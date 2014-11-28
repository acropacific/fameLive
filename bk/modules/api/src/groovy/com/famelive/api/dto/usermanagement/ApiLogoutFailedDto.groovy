package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIErrorCode
import com.famelive.api.exceptions.ApiException
import com.famelive.api.util.ApiMessagesUtil

/**
 * Created by anil on 26/9/14.
 */

@APIResponseClass
class ApiLogoutFailedDto extends ApiResponseDto {
    ApiLogoutFailedDto() {
        this.status = ApiConstants.MOBILE_API_ERROR_CODE
        this.message = "logout failed"
    }

    ApiLogoutFailedDto(ApiException exception) {
        APIErrorCode error = APIErrorCode.valueOf(exception.class.getSimpleName())
        this.status = ApiConstants.MOBILE_API_ERROR_CODE
        if (error) {
            this.code = error.errorCode
            String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
            this.message = msg ? msg : ""
        }

    }
    @APIResponseField(include = true)
    public String email
}
