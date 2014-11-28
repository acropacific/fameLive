package com.famelive.common.command.slotmanagement

import com.famelive.common.command.PaginationCommand
import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.exceptions.slotmanagement.BlankEventStatusException
import grails.validation.Validateable

@Validateable
class EventListCommand extends PaginationCommand {

    EventStatus eventStatus

    static constraints = {
        eventStatus nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventStatusException()
            }
        }
        max nullable: true
        order nullable: true
        sort nullable: true
        offset nullable: true
    }
}
