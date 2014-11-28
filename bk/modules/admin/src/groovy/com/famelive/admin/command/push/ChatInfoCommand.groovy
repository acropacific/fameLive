package com.famelive.admin.command.push

import grails.validation.Validateable

@Validateable
class ChatInfoCommand {

    String senderName
    String message
    Set<String> channels

    static constraints = {
        message nullable: false, blank: false
        channels nullable: false, blank: false
        senderName nullable: true
    }
}
