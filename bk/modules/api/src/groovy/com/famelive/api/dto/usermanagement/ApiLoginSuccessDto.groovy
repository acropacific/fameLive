package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil

@APIResponseClass
class ApiLoginSuccessDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String access_token
    @APIResponseField(include = true)
    public String email
    @APIResponseField(include = true)
    public String roles
    @APIResponseField(include = true)
    public String userId

    ApiLoginSuccessDto() {
        this.code = APIActions.LOGIN.successCode
        String msg = ApiMessagesUtil.messageSource.getProperty("${this.code}")
        this.message = msg ? msg : ""
    }

}
