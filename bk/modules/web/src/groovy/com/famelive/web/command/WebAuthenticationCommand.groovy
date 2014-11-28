package com.famelive.web.command

import com.famelive.common.command.RequestCommand

abstract class WebAuthenticationCommand extends WebRequestCommand {
    Long id

    abstract RequestCommand toRequestCommand()
}
