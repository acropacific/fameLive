package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.slotmanagement.Event

class EventImageDto extends ResponseDto {

    EventImageDto() {}

    EventImageDto(Event event) {
    }

    static EventImageDto createCommonResponseDto(Event event) {
        return new EventImageDto(event)
    }

}
