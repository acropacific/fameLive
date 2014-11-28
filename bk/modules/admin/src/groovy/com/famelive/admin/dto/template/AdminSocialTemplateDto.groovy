package com.famelive.admin.dto.template

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.template.SocialTemplate

class AdminSocialTemplateDto extends AdminResponseDto {

    List<AdminSocialTemplateDetailDto> adminSocialTemplateDetailDtos

    AdminSocialTemplateDto() {}

    AdminSocialTemplateDto(SocialTemplateDto socialTemplateDto) {
        this.adminSocialTemplateDetailDtos = populateSocialTemplateDetailDto(socialTemplateDto)
    }

    static AdminSocialTemplateDto createCommonResponseDto(SocialTemplateDto socialTemplateDto) {
        return new AdminSocialTemplateDto(socialTemplateDto)
    }

    static List<AdminSocialTemplateDetailDto> populateSocialTemplateDetailDto(SocialTemplateDto socialTemplateDto) {
        List<AdminSocialTemplateDetailDto> adminSocialTemplateDetailDtoList = []
        socialTemplateDto?.socialTemplates?.each { SocialTemplate socialTemplate ->
            adminSocialTemplateDetailDtoList << AdminSocialTemplateDetailDto.createAdminResponseDto(socialTemplate)
        }
        return adminSocialTemplateDetailDtoList
    }
}
