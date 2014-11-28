package com.famelive.api.dto.usermanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.FetchSocialAccountDto
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.user.LinkedSocialAccount

@APIResponseClass
class ApiFetchSocialAccountDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public List<ApiLinkedSocialAccount> linkedSocialAccounts

    ApiFetchSocialAccountDto() {}

    ApiFetchSocialAccountDto(FetchSocialAccountDto fetchSocialAccountDto) {
        this.linkedSocialAccounts = populateLinkedSocialAccountList(fetchSocialAccountDto?.linkedSocialAccounts)
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FETCH_SOCIAL_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchSocialAccountDto createApiResponseDto(FetchSocialAccountDto fetchSocialAccountDto) {
        return new ApiFetchSocialAccountDto(fetchSocialAccountDto)
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
