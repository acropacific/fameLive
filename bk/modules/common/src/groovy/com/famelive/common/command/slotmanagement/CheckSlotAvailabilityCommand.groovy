package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankEventDurationException
import com.famelive.common.exceptions.slotmanagement.BlankEventStartTimeException
import grails.validation.Validateable

@Validateable
class CheckSlotAvailabilityCommand extends AuthenticationTokenCommand {

    Date startTime
    Long duration

    static constraints = {
        startTime nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventStartTimeException()
            }
        }
        duration nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventDurationException()
            }
        }
    }
}
