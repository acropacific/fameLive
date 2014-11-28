package com.famelive.api.dto.followermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.followermanagement.FollowPerformerDto

@APIResponseClass
class ApiFollowPerformerDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public Boolean isFollower
    @APIResponseField(include = true)
    public Long totalFollowers

    ApiFollowPerformerDto() {
    }

    ApiFollowPerformerDto(FollowPerformerDto followPerformerDto) {
        this.isFollower = followPerformerDto?.isFollower
        this.totalFollowers = followPerformerDto?.totalFollowers
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FOLLOW.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFollowPerformerDto createApiResponseDto(FollowPerformerDto followPerformerDto) {
        return new ApiFollowPerformerDto(followPerformerDto)
    }
}
