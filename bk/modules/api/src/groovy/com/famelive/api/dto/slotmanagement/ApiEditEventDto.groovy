package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.EventDto

@APIResponseClass
class ApiEditEventDto extends ApiResponseDto {
    @APIResponseField(include = true)
    public String name
    @APIResponseField(include = true)
    public String description
    @APIResponseField(include = true)
    public String eventId
    @APIResponseField(include = true)
    public String imageName
    @APIResponseField(include = true)
    public Long duration
    @APIResponseField(include = true)
    public String startTime
    @APIResponseField(include = true)
    public String endTime
    @APIResponseField(include = true)
    public String imageFolder

    ApiEditEventDto() {}

    ApiEditEventDto(EventDto eventDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.EDIT_EVENT.successCode
        this.name = eventDto?.name
        this.eventId = eventDto?.eventId
        this.description = eventDto?.description ?: ""
        this.imageName = eventDto?.imageName ?: ""
        this.duration = eventDto?.duration
        this.imageFolder=eventDto?.imagePath
        this.startTime = eventDto?.startTime?.format(ApiConstants.DATE_TIME_FORMAT)
        this.endTime = eventDto?.endTime?.format(ApiConstants.DATE_TIME_FORMAT)
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiEditEventDto createApiResponseDto(EventDto eventDto) {
        return new ApiEditEventDto(eventDto)
    }
}
