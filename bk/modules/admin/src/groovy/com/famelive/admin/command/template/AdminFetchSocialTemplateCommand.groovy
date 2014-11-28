package com.famelive.admin.command.template

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.admin.enums.AdminSocialAccount
import com.famelive.common.command.template.FetchSocialTemplateCommand

class AdminFetchSocialTemplateCommand extends AdminAuthenticationCommand {

    AdminSocialAccount adminSocialAccount

    @Override
    FetchSocialTemplateCommand toRequestCommand() {
        return new FetchSocialTemplateCommand(id: this?.id, socialAccounts: adminSocialAccount?.socialAccounts)
    }
}
