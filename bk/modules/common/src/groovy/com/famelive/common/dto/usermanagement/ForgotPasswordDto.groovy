package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class ForgotPasswordDto extends ResponseDto {

    ForgotPasswordDto() {}

    ForgotPasswordDto(User user) {}

    static ForgotPasswordDto createCommonResponseDto(User user) {
        return new ForgotPasswordDto(user)
    }

}
