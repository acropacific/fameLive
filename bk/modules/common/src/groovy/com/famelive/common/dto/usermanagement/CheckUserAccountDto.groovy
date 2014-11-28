package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class CheckUserAccountDto extends ResponseDto {

    Boolean isAccountVerified

    CheckUserAccountDto() {}

    CheckUserAccountDto(User user) {
        this.isAccountVerified = user?.isAccountVerified
    }

    static CheckUserAccountDto createCommonResponseDto(User user) {
        return new CheckUserAccountDto(user)
    }
}
