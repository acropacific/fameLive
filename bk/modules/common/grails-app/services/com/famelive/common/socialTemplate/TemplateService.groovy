package com.famelive.common.socialTemplate

import com.famelive.common.command.template.FetchPushNotificationTemplateCommand
import com.famelive.common.command.template.FetchSocialTemplateCommand
import com.famelive.common.dto.socialtemplate.NotificationTemplateDto
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.template.NotificationTemplate
import com.famelive.common.template.SocialTemplate

class TemplateService {

    def userService

    SocialTemplateDto fetchSocialTemplate(FetchSocialTemplateCommand fetchSocialTemplateCommand) {
        fetchSocialTemplateCommand.validate()
        List<SocialTemplate> socialTemplates=SocialTemplate.findAllBySocialAccountInList(fetchSocialTemplateCommand.socialAccounts)
        SocialTemplateDto socialTemplateDto=SocialTemplateDto.createCommonResponseDto(socialTemplates)
        socialTemplateDto
    }

    NotificationTemplateDto fetchPushNotificationTemplate(FetchPushNotificationTemplateCommand fetchPushNotificationTemplateCommand) {
        fetchPushNotificationTemplateCommand.validate()
        List<NotificationTemplate> notificationTemplates=NotificationTemplate.findAllByNotificationInList(fetchPushNotificationTemplateCommand.notifications)
        NotificationTemplateDto notificationTemplateDto=NotificationTemplateDto.createCommonResponseDto(notificationTemplates)
        notificationTemplateDto
    }
}
