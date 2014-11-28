package com.famelive.api.command

import com.famelive.common.command.RequestCommand
import grails.validation.Validateable

@Validateable
abstract class ApiRequestCommand {
    String actionName
    String apiVersion
    String appKey

    abstract RequestCommand toRequestCommand()
}
