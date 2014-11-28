package com.famelive.api.command.usermanagement

import com.famelive.api.command.ApiAuthenticationTokenCommand
import com.famelive.api.enums.ApiSocialAccount
import com.famelive.common.command.template.FetchSocialTemplateCommand
import grails.validation.Validateable

@Validateable
class ApiFetchSocialTemplateCommand extends ApiAuthenticationTokenCommand {

    List<ApiSocialAccount> socialAccounts

    static constraints = {
        socialAccounts nullable: true
    }

    @Override
    FetchSocialTemplateCommand toRequestCommand() {
        return new FetchSocialTemplateCommand(id: this?.id, socialAccounts: this?.socialAccounts?.socialAccount)
    }
}
