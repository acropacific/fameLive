package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserProfileDto

@APIResponseClass
class ApiFetchPerformerProfileDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String id
    @APIResponseField(include = true)
    public String fameId
    @APIResponseField(include = true)
    public String username
    @APIResponseField(include = true)
    public String email
    @APIResponseField(include = true)
    public String fameName
    @APIResponseField(include = true)
    public String mobile
    @APIResponseField(include = true)
    public String imageName
    @APIResponseField(include = true)
    public String dateCreated
    @APIResponseField(include = true)
    public Boolean isFollower
    @APIResponseField(include = true)
    public Long totalFollowers


    ApiFetchPerformerProfileDto() {}

    ApiFetchPerformerProfileDto(UserProfileDto userProfileDto) {
        this.id = userProfileDto?.id
        this.fameId = userProfileDto?.fameId
        this.username = userProfileDto.username
        this.fameName = userProfileDto.fameName ?: ''
        this.email = userProfileDto.email
        this.mobile = userProfileDto?.mobile ?: ''
        this.imageName = userProfileDto?.imageName ?: ''
        this.dateCreated = userProfileDto?.dateCreated?.format(ApiConstants.DATE_TIME_FORMAT)
        this.isFollower = userProfileDto?.isFollower
        this.totalFollowers = userProfileDto?.totalFollowers
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.GET_PROFILE_DATA.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchPerformerProfileDto createApiResponseDto(UserProfileDto userProfileDto) {
        return new ApiFetchPerformerProfileDto(userProfileDto)
    }

}
