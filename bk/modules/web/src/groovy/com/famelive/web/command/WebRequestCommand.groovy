package com.famelive.web.command

import com.famelive.common.command.RequestCommand

abstract class WebRequestCommand {

    abstract RequestCommand toRequestCommand()
}
