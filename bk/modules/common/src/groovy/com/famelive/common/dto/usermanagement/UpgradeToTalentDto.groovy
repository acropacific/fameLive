package com.famelive.common.dto.usermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.User

class UpgradeToTalentDto extends ResponseDto {

    UpgradeToTalentDto() {}

    UpgradeToTalentDto(User user) {}

    static UpgradeToTalentDto createCommonResponseDto(User user) {
        return new UpgradeToTalentDto(user)
    }

}
