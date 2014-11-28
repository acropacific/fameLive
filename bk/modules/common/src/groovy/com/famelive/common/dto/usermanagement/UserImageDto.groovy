package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto

class UserImageDto extends ResponseDto {

    UserImageDto() {}

    static UserImageDto createCommonResponseDto() {
        return new UserImageDto()
    }

}
