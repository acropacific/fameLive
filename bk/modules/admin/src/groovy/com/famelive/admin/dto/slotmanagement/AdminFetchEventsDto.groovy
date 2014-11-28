package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.slotmanagement.EventDetailDto
import com.famelive.common.dto.slotmanagement.FetchEventsDto

class AdminFetchEventsDto extends AdminResponseDto {

    List<AdminEventDetailDto> apiEventDetailDtos
    Integer eventCount

    AdminFetchEventsDto() {}

    AdminFetchEventsDto(FetchEventsDto fetchEventsDto) {
        this.apiEventDetailDtos = populateEventDetailDto(fetchEventsDto)
        this.eventCount = fetchEventsDto.eventCount
    }

    static AdminFetchEventsDto createAdminResponseDto(FetchEventsDto fetchEventsDto) {
        return new AdminFetchEventsDto(fetchEventsDto)
    }

    static List<AdminEventDetailDto> populateEventDetailDto(FetchEventsDto fetchEventsDto) {
        List<AdminEventDetailDto> apiEventDetailDtos = []
        if (fetchEventsDto?.eventDetailDtos) {
            fetchEventsDto?.eventDetailDtos?.each { EventDetailDto eventDetailDto ->
                apiEventDetailDtos << new AdminEventDetailDto(eventDetailDto)
            }
        }
        return apiEventDetailDtos
    }
}
