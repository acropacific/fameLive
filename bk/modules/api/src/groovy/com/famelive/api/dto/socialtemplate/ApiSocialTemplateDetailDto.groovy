package com.famelive.api.dto.socialtemplate

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.dto.ApiResponseDto
import com.famelive.common.template.SocialTemplate

@APIResponseClass
class ApiSocialTemplateDetailDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public String socialAccount
    @APIResponseField(include = true)
    public String socialMessage

    ApiSocialTemplateDetailDto() {
    }

    ApiSocialTemplateDetailDto(SocialTemplate socialTemplate) {
        this.socialMessage = socialTemplate?.message
        this.socialAccount = socialTemplate?.socialAccount
    }

    static ApiSocialTemplateDetailDto createApiResponseDto(SocialTemplate socialTemplate) {
        return new ApiSocialTemplateDetailDto(socialTemplate)
    }
}
