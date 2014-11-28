package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UpgradeToTalentDto

@APIResponseClass
class ApiUpgradeToTalentDto extends ApiResponseDto {

    ApiUpgradeToTalentDto() {
    }

    ApiUpgradeToTalentDto(UpgradeToTalentDto toTalentDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.UPGRADE_TO_TALENT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiUpgradeToTalentDto createApiResponseDto(UpgradeToTalentDto toTalentDto) {
        return new ApiUpgradeToTalentDto(toTalentDto)
    }
}
