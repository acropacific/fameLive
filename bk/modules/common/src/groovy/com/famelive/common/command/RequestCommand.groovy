package com.famelive.common.command

import grails.validation.Validateable

@Validateable
abstract class RequestCommand {
    String actionName
    String apiVersion
    String appKey

    static constraints = {
        actionName nullable: true
        apiVersion nullable: true
        appKey nullable: true
    }
}
