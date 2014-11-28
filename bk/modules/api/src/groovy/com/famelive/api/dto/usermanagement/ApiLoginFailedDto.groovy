package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIErrorCode
import com.famelive.api.exceptions.ApiException
import com.famelive.api.exceptions.ApiUserAccountBlockException
import com.famelive.api.util.ApiMessagesUtil

@APIResponseClass
class ApiLoginFailedDto extends ApiResponseDto {

    ApiLoginFailedDto() {
        this.status = ApiConstants.MOBILE_API_ERROR_CODE
        this.message = "login failed"
        this.code = APIErrorCode.ApiInvalidLoginCredentialException.errorCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    ApiLoginFailedDto(ApiException exception) {
        APIErrorCode error = APIErrorCode.valueOf(exception.class.getSimpleName())
        this.status = ApiConstants.MOBILE_API_ERROR_CODE
        if (error) {
            this.code = error.errorCode
            String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
            this.message = msg ? msg : ""
        }

    }
}
