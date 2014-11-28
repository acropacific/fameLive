package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.EventDto

@APIResponseClass
class ApiEventDto extends ApiResponseDto {
    @APIResponseField(include = true)
    public String eventUId
    @APIResponseField(include = true)
    public String name
    @APIResponseField(include = true)
    public String description
    @APIResponseField(include = true)
    public String eventId
    @APIResponseField(include = true)
    public String imageFolder

    ApiEventDto() {}

    ApiEventDto(EventDto eventDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CREATE_EVENT.successCode
        this.name = eventDto?.name
        this.eventUId = eventDto?.eventUId
        this.eventId = eventDto?.eventId
        this.imageFolder = eventDto?.imagePath
        this.description = eventDto?.description ?: ''
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiEventDto createApiResponseDto(EventDto eventDto) {
        return new ApiEventDto(eventDto)
    }
}
