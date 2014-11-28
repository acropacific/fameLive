package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankEventIdException
import com.famelive.common.exceptions.slotmanagement.BlankEventImageNameException
import com.famelive.common.exceptions.slotmanagement.EventNotFoundException
import com.famelive.common.slotmanagement.Event
import grails.validation.Validateable

@Validateable
class EventImageCommand extends AuthenticationTokenCommand {
    String eventId
    String imageName

    static constraints = {
        eventId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventIdException()
            } else if (!Event.get(val)) {
                throw new EventNotFoundException()
            }
        }
        imageName nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventImageNameException()
            }
        }
    }
}
