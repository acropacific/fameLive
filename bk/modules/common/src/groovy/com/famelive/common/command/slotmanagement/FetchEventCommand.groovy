package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankEventIdException
import com.famelive.common.exceptions.slotmanagement.EventNotFoundException
import com.famelive.common.slotmanagement.Event
import grails.validation.Validateable

@Validateable
class FetchEventCommand extends AuthenticationTokenCommand {

    Long eventId

    static constraints = {
        eventId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventIdException()
            } else if (!Event.get(val)) {
                throw new EventNotFoundException()
            }
        }
    }
}
