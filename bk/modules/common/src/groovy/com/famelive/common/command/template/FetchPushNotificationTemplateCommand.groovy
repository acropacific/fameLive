package com.famelive.common.command.template

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.enums.SystemPushNotification
import com.famelive.common.exceptions.BlankSocialAccountException
import grails.validation.Validateable

@Validateable
class FetchPushNotificationTemplateCommand extends AuthenticationTokenCommand {

    List<SystemPushNotification> notifications

    static constraints = {
        notifications nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankSocialAccountException()
            }
        }
    }
}
