package com.famelive.api.dto.followermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.followermanagement.UnFollowPerformerDto

@APIResponseClass
class ApiUnFollowPerformerDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public Boolean isFollower
    @APIResponseField(include = true)
    public Long totalFollowers

    ApiUnFollowPerformerDto() {
    }

    ApiUnFollowPerformerDto(UnFollowPerformerDto unFollowPerformerDto) {
        this.isFollower = unFollowPerformerDto?.isFollower
        this.totalFollowers = unFollowPerformerDto?.totalFollowers
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.UN_FOLLOW.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiUnFollowPerformerDto createApiResponseDto(UnFollowPerformerDto unFollowPerformerDto) {
        return new ApiUnFollowPerformerDto(unFollowPerformerDto)
    }
}
