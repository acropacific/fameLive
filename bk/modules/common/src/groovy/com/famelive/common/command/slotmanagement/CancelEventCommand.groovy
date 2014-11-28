package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankEventIdException
import com.famelive.common.exceptions.slotmanagement.EventCancelTimeInvalidException
import com.famelive.common.exceptions.slotmanagement.EventNotFoundException
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.DateTimeUtil
import grails.validation.Validateable

@Validateable
class CancelEventCommand extends AuthenticationTokenCommand {
    String eventId

    static constraints = {
        eventId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventIdException()
            } else {
                Event event=Event.get(val)
                if (!event) {
                    throw new EventNotFoundException()
                } else if (event.startTime <= new Date()) {
                    throw new EventCancelTimeInvalidException()
                }
            }
        }
    }
}
