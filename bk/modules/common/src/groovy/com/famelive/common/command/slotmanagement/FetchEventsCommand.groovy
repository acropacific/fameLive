package com.famelive.common.command.slotmanagement

import com.famelive.common.command.PaginationCommand
import grails.validation.Validateable

@Validateable
class FetchEventsCommand extends PaginationCommand {

    String memberName
    String email
    Date startDateTime
    Date endDateTime
    Long genreId

    static constraints={
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }
}
