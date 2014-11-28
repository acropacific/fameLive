package com.famelive.common.dto.socialtemplate

import com.famelive.common.dto.ResponseDto
import com.famelive.common.user.SocialTemplate

class SocialTemplateDto extends ResponseDto {

    SocialTemplate socialTemplate

    SocialTemplateDto() {}

    SocialTemplateDto(SocialTemplate socialTemplate) {
        this.socialTemplate = socialTemplate
    }

    static SocialTemplateDto createCommonResponseDto(SocialTemplate socialTemplate) {
        return new SocialTemplateDto(socialTemplate)
    }

}
