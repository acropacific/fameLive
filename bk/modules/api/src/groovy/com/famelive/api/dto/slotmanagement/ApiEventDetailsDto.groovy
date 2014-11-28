package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.EventDetailsDto

@APIResponseClass
public class ApiEventDetailsDto extends ApiEventDetailDto {

    @APIResponseField(include = true)
    public String eventStatus

    @APIResponseField(include = true)
    public Map eventUrls

    @APIResponseField(include = true)
    public String eventUId

    ApiEventDetailsDto() {}

    ApiEventDetailsDto(EventDetailsDto eventDetailsDto) {
        super(eventDetailsDto)
        this.eventStatus = eventDetailsDto.eventStatus
        this.eventUrls = eventDetailsDto.eventUrls
        this.eventUId = eventDetailsDto.eventUId

        this.code = APIActions.FETCH_EVENT_DETAILS.successCode
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiEventDetailsDto createApiResponseDto(EventDetailsDto eventDetailsDto) {
        return new ApiEventDetailsDto(eventDetailsDto)
    }
}
