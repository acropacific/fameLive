package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserImageDto

@APIResponseClass
class ApiUserImageDto extends ApiResponseDto {

    ApiUserImageDto() {
    }

    ApiUserImageDto(UserImageDto userImageDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.USER_IMAGE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiUserImageDto createApiResponseDto(UserImageDto userImageDto) {
        return new ApiUserImageDto(userImageDto)
    }
}
