package com.famelive.common.socialTemplate

import com.famelive.common.command.usernamagement.FetchSocialTemplateCommand
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.user.SocialTemplate

class SocialTemplateService {

    def userService

    SocialTemplateDto fetchSocialTemplate(FetchSocialTemplateCommand fetchSocialTemplateCommand) {
        fetchSocialTemplateCommand.validate()
        SocialTemplate socialTemplate = SocialTemplate.findBySocialAccount(fetchSocialTemplateCommand.socialAccount)
        SocialTemplateDto socialTemplateDto = SocialTemplateDto.createCommonResponseDto(socialTemplate)
        socialTemplateDto
    }
}
