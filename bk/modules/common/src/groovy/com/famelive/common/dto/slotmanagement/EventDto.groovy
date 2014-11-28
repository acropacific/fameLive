package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.slotmanagement.Event

class EventDto extends ResponseDto {
    String eventId
    String eventUId
    String name
    String description
    Date startTime
    Date endTime
    GenreDto genre
    Long duration
    String imageName
    UserProfileDto user
    String imagePath

    EventDto() {}

    EventDto(Event event) {
        this.eventId = event.id
        this.eventUId = event.eventId
        this.name = event.name
        this.description = event.description
        this.startTime = event.startTime
        this.endTime = event.endTime
        this.genre = new GenreDto(event?.genres?.first())
        this.duration = event.duration
        this.imageName = event?.imageName
        this.imagePath = populateEventImagePath(event)
        this.user = new UserProfileDto(event?.user)
    }

    static EventDto createCommonResponseDto(Event event) {
        return new EventDto(event)
    }

    static String populateEventImagePath(Event event) {
        return event?.user?.fameId + "/" + event?.eventId
    }

}
