package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.CheckUserAccountDto
import com.famelive.common.dto.usermanagement.VerifyUserEmailDto

@APIResponseClass
class ApiCheckUserAccountDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public Boolean isAccountVerified

    ApiCheckUserAccountDto() {}

    ApiCheckUserAccountDto(CheckUserAccountDto checkUserAccountDto) {
        this.isAccountVerified = checkUserAccountDto?.isAccountVerified
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CHECK_USER_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiCheckUserAccountDto createApiResponseDto(CheckUserAccountDto checkUserAccountDto) {
        return new ApiCheckUserAccountDto(checkUserAccountDto)

    }

}
