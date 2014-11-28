package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.EventDetailDto
import com.famelive.common.dto.slotmanagement.FetchEventsDto

@APIResponseClass
class ApiFetchEventsDto extends ApiResponseDto {

    @APIResponseField(include = true, key = "events")
    public List<ApiEventDetailDto> apiEventDetailDtos

    ApiFetchEventsDto() {}

    ApiFetchEventsDto(FetchEventsDto fetchEventsDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FETCH_EVENT.successCode
        this.apiEventDetailDtos = populateEventDetailDto(fetchEventsDto)
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchEventsDto createApiResponseDto(FetchEventsDto fetchEventsDto) {
        return new ApiFetchEventsDto(fetchEventsDto)
    }

    static List<ApiEventDetailDto> populateEventDetailDto(FetchEventsDto fetchEventsDto) {
        List<ApiEventDetailDto> apiEventDetailDtos = []
        if (fetchEventsDto?.eventDetailDtos) {
            fetchEventsDto?.eventDetailDtos?.each { EventDetailDto eventDetailDto ->
                apiEventDetailDtos << new ApiEventDetailDto(eventDetailDto)
            }
        }
        return apiEventDetailDtos
    }
}
