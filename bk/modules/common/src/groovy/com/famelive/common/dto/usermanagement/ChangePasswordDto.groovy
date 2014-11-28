package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class ChangePasswordDto extends ResponseDto {

    ChangePasswordDto() {}

    ChangePasswordDto(User user) {}

    static ChangePasswordDto createCommonResponseDto(User user) {
        return new ChangePasswordDto(user)
    }

}
