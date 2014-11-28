package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class VerifyUserEmailDto extends ResponseDto {

    VerifyUserEmailDto() {}

    VerifyUserEmailDto(User user) {
    }

    static VerifyUserEmailDto createCommonResponseDto(User user) {
        return new VerifyUserEmailDto(user)
    }
}
