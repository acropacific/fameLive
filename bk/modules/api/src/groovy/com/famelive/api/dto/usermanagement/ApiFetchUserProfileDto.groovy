package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.user.LinkedSocialAccount

@APIResponseClass
class ApiFetchUserProfileDto extends ApiResponseDto {

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
    public String medium
    @APIResponseField(include = true)
    public String userType
    @APIResponseField(include = true)
    public List<ApiLinkedSocialAccount> linkedSocialAccounts
    @APIResponseField(include = true)
    public String imageFolder
    @APIResponseField(include = true)
    public Long totalFollowers


    ApiFetchUserProfileDto() {}

    ApiFetchUserProfileDto(UserProfileDto userProfileDto) {
        this.username = userProfileDto.username
        this.fameName = userProfileDto.fameName ?: ''
        this.email = userProfileDto.email
        this.mobile = userProfileDto?.mobile ?: ''
        this.imageName = userProfileDto?.imageName ?: ''
        this.dateCreated = userProfileDto?.dateCreated?.format(ApiConstants.DATE_TIME_FORMAT)
        this.medium = userProfileDto?.registrationType?.toString()
        this.userType = userProfileDto?.type?.toString()
        this.imageFolder = userProfileDto?.imageFolder
        this.totalFollowers = userProfileDto?.totalFollowers
        this.linkedSocialAccounts = populateLinkedSocialAccountList(userProfileDto?.linkedSocialAccounts)
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.GET_PROFILE_DATA.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchUserProfileDto createApiResponseDto(UserProfileDto userProfileDto) {
        return new ApiFetchUserProfileDto(userProfileDto)
    }

    static List<ApiLinkedSocialAccount> populateLinkedSocialAccountList(List<LinkedSocialAccount> linkedSocialAccounts) {
        List<ApiLinkedSocialAccount> apiLinkedSocialAccountList = []
        if (linkedSocialAccounts) {
            linkedSocialAccounts.each { LinkedSocialAccount socialAccount ->
                apiLinkedSocialAccountList << ApiLinkedSocialAccount.createApiResponseDto(socialAccount)
            }
        }
        return apiLinkedSocialAccountList
    }
}
