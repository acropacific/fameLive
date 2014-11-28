package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.EventImageDto

@APIResponseClass
class ApiEventImageDto extends ApiResponseDto {

    ApiEventImageDto() {}

    ApiEventImageDto(EventImageDto eventDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.SAVE_EVENT_IMAGE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiEventImageDto createApiResponseDto(EventImageDto eventDto) {
        return new ApiEventImageDto(eventDto)
    }
}
