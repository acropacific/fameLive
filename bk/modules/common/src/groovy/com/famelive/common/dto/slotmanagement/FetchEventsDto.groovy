package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.slotmanagement.Event

class FetchEventsDto extends ResponseDto {
    List<EventDetailDto> eventDetailDtos
    Integer eventCount

    FetchEventsDto() {}

    FetchEventsDto(List<Event> eventList) {
        this.eventDetailDtos = populateFetchEventDetailsDto(eventList)
    }

    FetchEventsDto(List<Event> eventList, Integer eventCount) {
        this.eventDetailDtos = populateFetchEventDetailsDto(eventList)
        this.eventCount = eventCount
    }

    static FetchEventsDto createCommonResponseDto(List<Event> eventList) {
        return new FetchEventsDto(eventList)
    }

    static FetchEventsDto createCommonResponseDto(List<Event> eventList, Integer eventCount) {
        return new FetchEventsDto(eventList, eventCount)
    }

    static List<EventDetailDto> populateFetchEventDetailsDto(List<Event> eventList) {
        List<EventDetailDto> eventDetailDtos = []
        if (eventList) {
            eventList.each { Event event ->
                eventDetailDtos << new EventDetailDto(event)
            }
        }
        return eventDetailDtos
    }

}
