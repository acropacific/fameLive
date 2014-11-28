package com.famelive.common.dto.socialtemplate

import com.famelive.common.dto.ResponseDto
import com.famelive.common.template.SocialTemplate

class SocialTemplateDto extends ResponseDto {

    List<SocialTemplate> socialTemplates

    SocialTemplateDto() {}

    SocialTemplateDto(List<SocialTemplate> socialTemplates) {
        this.socialTemplates = socialTemplates
    }

    static SocialTemplateDto createCommonResponseDto(List<SocialTemplate> socialTemplates) {
        return new SocialTemplateDto(socialTemplates)
    }

}
