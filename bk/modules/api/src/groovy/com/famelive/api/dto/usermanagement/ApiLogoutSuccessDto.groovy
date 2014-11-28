package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil

/**
 * Created by anil on 26/9/14.
 */

@APIResponseClass
class ApiLogoutSuccessDto extends ApiResponseDto {
    ApiLogoutSuccessDto() {
        this.code = APIActions.LOGOUT.successCode
        String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.message = msg ? msg : ""
    }
}
