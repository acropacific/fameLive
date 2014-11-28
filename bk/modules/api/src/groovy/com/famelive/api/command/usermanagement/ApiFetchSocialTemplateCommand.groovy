package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.api.enums.ApiSocialAccount
import com.famelive.common.command.usernamagement.FetchSocialTemplateCommand
import grails.validation.Validateable

@Validateable
class ApiFetchSocialTemplateCommand extends ApiAuthenticationTokenCommand {

    ApiSocialAccount socialAccount

    static constraints = {
        socialAccount nullable: true
    }

    @Override
    FetchSocialTemplateCommand toRequestCommand() {
        return new FetchSocialTemplateCommand(id: this?.id, socialAccount: this?.socialAccount?.socialAccount)
    }
}
