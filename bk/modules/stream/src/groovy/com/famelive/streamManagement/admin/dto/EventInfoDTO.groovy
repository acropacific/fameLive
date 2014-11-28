package com.famelive.streamManagement.admin.dto

import com.famelive.common.slotmanagement.Event

/**
 * Created by anil on 21/11/14.
 */
class EventInfoDTO {

    String eventUId
    String name
    String eventPerformer
    String eventPerformerEmail
    Date eventStartTime
    int eventDuration
    String eventGenre
    long eventId

    EventInfoDTO(Event event) {
        this.eventUId = event.eventId
        this.name = event.name
        this.eventPerformer = event.user.username
        this.eventPerformerEmail = event.user.email
        this.eventStartTime = event.startTime
        this.eventDuration = event.duration
        this.eventGenre = event.genres*.name
        this.eventId = event.id
    }

    EventInfoDTO(long eventId) {
        this(Event.findById(eventId))
    }
}
