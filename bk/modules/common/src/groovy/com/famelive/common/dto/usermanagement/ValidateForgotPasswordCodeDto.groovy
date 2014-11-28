package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class ValidateForgotPasswordCodeDto extends ResponseDto {
    String successToken
    User user

    ValidateForgotPasswordCodeDto() {}

    ValidateForgotPasswordCodeDto(User user, String successToken) {
        this.user = user
        this.successToken = successToken
    }

    ValidateForgotPasswordCodeDto(User user) {
        this.user = user
    }

    static ValidateForgotPasswordCodeDto createCommonResponseDto(User user) {
        return new ValidateForgotPasswordCodeDto(user)
    }

    static ValidateForgotPasswordCodeDto createCommonResponseDto(User user, String successToken) {
        return new ValidateForgotPasswordCodeDto(user, successToken)
    }
}
