package com.famelive.api.dto.socialtemplate

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.template.SocialTemplate

@APIResponseClass
class ApiSocialTemplateDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public List<ApiSocialTemplateDetailDto> socialTemplates

    ApiSocialTemplateDto() {
    }

    ApiSocialTemplateDto(SocialTemplateDto socialTemplateDto) {
        this.socialTemplates = populateSocialTemplateDetailDto(socialTemplateDto)
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FETCH_SOCIAL_ACCOUNT.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiSocialTemplateDto createApiResponseDto(SocialTemplateDto socialTemplateDto) {
        return new ApiSocialTemplateDto(socialTemplateDto)
    }

    static List<ApiSocialTemplateDetailDto> populateSocialTemplateDetailDto(SocialTemplateDto socialTemplateDto) {
        List<ApiSocialTemplateDetailDto> apiSocialTemplateDetailDtoList = []
        socialTemplateDto?.socialTemplates?.each { SocialTemplate socialTemplate ->
            apiSocialTemplateDetailDtoList << ApiSocialTemplateDetailDto.createApiResponseDto(socialTemplate)
        }
        return apiSocialTemplateDetailDtoList
    }
}
