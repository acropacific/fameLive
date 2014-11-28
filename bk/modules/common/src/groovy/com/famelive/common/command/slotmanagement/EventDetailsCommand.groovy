package com.famelive.common.command.slotmanagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.slotmanagement.BlankEventIdException
import com.famelive.common.exceptions.slotmanagement.EventNotFoundException
import com.famelive.common.slotmanagement.Event
import grails.validation.Validateable

@Validateable
class EventDetailsCommand extends RequestCommand {

    String eventID

    static constraints = {
        eventID nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventIdException()
            } else if (!Event.findByEventId(val)) {
                throw new EventNotFoundException()
            }
        }
    }
}
