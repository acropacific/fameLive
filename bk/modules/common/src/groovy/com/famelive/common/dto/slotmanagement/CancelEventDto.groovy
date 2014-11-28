package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.slotmanagement.Event

class CancelEventDto extends ResponseDto {

    CancelEventDto() {}

    CancelEventDto(Event event) {
    }

    static CancelEventDto createCommonResponseDto(Event event) {
        return new CancelEventDto(event)
    }

}
