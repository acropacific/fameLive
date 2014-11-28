package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class ChangeEmailDto extends ResponseDto {

    ChangeEmailDto() {}

    ChangeEmailDto(User user) {
    }

    static ChangeEmailDto createCommonResponseDto(User user) {
        return new ChangeEmailDto(user)
    }
}
