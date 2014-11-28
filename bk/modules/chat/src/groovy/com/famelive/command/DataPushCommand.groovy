package com.famelive.command

import grails.validation.Validateable

@Validateable
class DataPushCommand {
    String message
    Set<String> channels
    String senderName
}
