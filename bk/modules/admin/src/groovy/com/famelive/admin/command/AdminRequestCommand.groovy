package com.famelive.admin.command

import com.famelive.common.command.RequestCommand

abstract class AdminRequestCommand {

    abstract RequestCommand toRequestCommand()
}
