package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto

class FameNameAvailabilityDto extends ResponseDto {

    FameNameAvailabilityDto() {}

    static FameNameAvailabilityDto createCommonResponseDto() {
        return new FameNameAvailabilityDto()
    }

}
