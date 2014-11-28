package com.famelive.admin.command

import com.famelive.common.command.RequestCommand

abstract class AdminAuthenticationCommand extends AdminRequestCommand {

    Long id

    abstract RequestCommand toRequestCommand()
}
