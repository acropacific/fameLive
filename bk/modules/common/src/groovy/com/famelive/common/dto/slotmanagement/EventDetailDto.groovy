package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.slotmanagement.Event

class EventDetailDto {
    Long eventId
    String eventUId
    String name
    String description
    Long duration
    Date startTime
    Date endTime
    String imageName
    FetchGenreDto genreDtos
    UserProfileDto user

    EventDetailDto() {}

    EventDetailDto(Event event) {
        this.eventId = event.id
        this.eventUId = event?.getEventId()
        this.name = event?.name
        this.description = event?.description
        this.duration = event?.duration
        this.startTime = event?.startTime
        this.endTime = event?.endTime
        this.imageName = event?.imageName
        this.genreDtos = FetchGenreDto.createCommonResponseDto(event?.genres as List)
        this.user = new UserProfileDto(event?.user)
    }
}
