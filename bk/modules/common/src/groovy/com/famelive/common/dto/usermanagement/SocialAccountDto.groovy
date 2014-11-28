package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class SocialAccountDto extends ResponseDto {

    SocialAccountDto() {}

    SocialAccountDto(User user) {}

    static SocialAccountDto createCommonResponseDto(User user) {
        return new SocialAccountDto(user)
    }

}
