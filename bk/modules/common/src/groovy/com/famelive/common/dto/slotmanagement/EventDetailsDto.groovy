package com.famelive.common.dto.slotmanagement

import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.slotmanagement.EventService
import com.famelive.common.streamManagement.EventStreamInfo
import grails.util.GrailsWebUtil

class EventDetailsDto extends EventDetailDto {

    EventStatus eventStatus
    Map eventUrls
    EventService eventService = GrailsWebUtil.currentApplication().mainContext.getBean("eventService")

    EventDetailsDto(EventStreamInfo eventStreamInfo) {
        super(eventStreamInfo.event)
        this.eventStatus = eventStreamInfo.event.status
        this.eventUrls = eventService.fetchEventUrls(eventStreamInfo)
    }

    static EventDetailsDto createCommonResponseDto(EventStreamInfo eventStreamInfo) {
        return new EventDetailsDto(eventStreamInfo)
    }
}
