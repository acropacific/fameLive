package com.famelive.api.dto.notification

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.dto.ApiResponseDto
import com.famelive.common.dto.notification.FetchNotificationChannelsDto

@APIResponseClass
class ApiFetchNotificationChannelsDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public List<String> channels

    ApiFetchNotificationChannelsDto() {

    }

    ApiFetchNotificationChannelsDto(FetchNotificationChannelsDto fetchNotificationChannelsDto) {
        this.channels = fetchNotificationChannelsDto.channels
    }

    static ApiFetchNotificationChannelsDto createApiResponseDto(FetchNotificationChannelsDto fetchNotificationChannelsDto) {
        return new ApiFetchNotificationChannelsDto(fetchNotificationChannelsDto)
    }
}
