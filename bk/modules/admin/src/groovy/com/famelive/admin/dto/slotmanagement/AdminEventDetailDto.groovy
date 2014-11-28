package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.common.dto.slotmanagement.EventDetailDto

class AdminEventDetailDto {

    Long eventId
    String eventUId
    String name
    String description
    Long duration
    Date startTime
    String imageName
    AdminGenreDto genre
    AdminUserProfileDto user

    AdminEventDetailDto() {}

    AdminEventDetailDto(EventDetailDto event) {
        this.eventId = event.eventId
        this.eventUId = event?.eventUId
        this.name = event?.name
        this.description = event?.description ?: ""
        this.duration = event?.duration
        this.startTime = event?.startTime
        this.imageName = event?.imageName ?: ""
        this.genre = new AdminGenreDto(event?.genreDtos?.genreDtos?.first())
        this.user = new AdminUserProfileDto(event?.user)
    }
}
