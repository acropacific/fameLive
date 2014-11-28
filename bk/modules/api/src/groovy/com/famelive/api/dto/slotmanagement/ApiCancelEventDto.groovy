package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.CancelEventDto
import com.famelive.common.dto.slotmanagement.EventImageDto

@APIResponseClass
class ApiCancelEventDto extends ApiResponseDto {

    ApiCancelEventDto() {}

    ApiCancelEventDto(CancelEventDto cancelEventDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CANCEL_EVENT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiCancelEventDto createApiResponseDto(CancelEventDto cancelEventDto) {
        return new ApiCancelEventDto(cancelEventDto)
    }
}
