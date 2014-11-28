package com.famelive.command

import grails.validation.Validateable

@Validateable
class DataPushCommand {
    String message
    List<String> channels
    String senderName
}
