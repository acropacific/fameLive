package com.famelive.admin.dto.template

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.template.SocialTemplate

class AdminSocialTemplateDetailDto extends AdminResponseDto {

    String socialAccount
    String socialMessage

    AdminSocialTemplateDetailDto() {
    }

    AdminSocialTemplateDetailDto(SocialTemplate socialTemplate) {
        this.socialMessage = socialTemplate?.message
        this.socialAccount = socialTemplate?.socialAccount
    }

    static AdminSocialTemplateDetailDto createAdminResponseDto(SocialTemplate socialTemplate) {
        return new AdminSocialTemplateDetailDto(socialTemplate)
    }
}
