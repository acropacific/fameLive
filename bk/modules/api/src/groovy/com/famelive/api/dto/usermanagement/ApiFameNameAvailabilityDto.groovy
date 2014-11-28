package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.FameNameAvailabilityDto

@APIResponseClass
class ApiFameNameAvailabilityDto extends ApiResponseDto {

    ApiFameNameAvailabilityDto() {
    }

    ApiFameNameAvailabilityDto(FameNameAvailabilityDto fameNameAvailabilityDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FAME_NAME_AVAILABLE.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFameNameAvailabilityDto createApiResponseDto(FameNameAvailabilityDto fameNameAvailabilityDto) {
        return new ApiFameNameAvailabilityDto(fameNameAvailabilityDto)
    }
}
