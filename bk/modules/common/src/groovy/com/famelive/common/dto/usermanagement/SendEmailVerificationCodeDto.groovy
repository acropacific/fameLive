package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class SendEmailVerificationCodeDto extends ResponseDto {

    SendEmailVerificationCodeDto() {}

    SendEmailVerificationCodeDto(User user) {
    }

    static SendEmailVerificationCodeDto createCommonResponseDto(User user) {
        return new SendEmailVerificationCodeDto(user)
    }
}
