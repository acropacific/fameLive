package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto

class UpdateForgotPasswordDto extends ResponseDto {

    UpdateForgotPasswordDto() {}

    static UpdateForgotPasswordDto createCommonResponseDto() {
        return new UpdateForgotPasswordDto()
    }
}
