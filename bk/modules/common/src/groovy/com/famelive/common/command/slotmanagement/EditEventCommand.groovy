package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.constant.CommonConstants
import com.famelive.common.constant.Constraints
import com.famelive.common.exceptions.slotmanagement.*
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.DateTimeUtil
import grails.validation.Validateable

@Validateable
class EditEventCommand extends AuthenticationTokenCommand {
    Long eventId
    String name
    String description
    Date startTime
    Long duration = CommonConstants.DEFAULT_EVENT_DURATION
    String imageName
    List<Long> genreIds

    Date getEndTime() {
        DateTimeUtil.addMinutesToDate(startTime, duration as Integer)
    }

    static constraints = {
        eventId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventIdException()
            } else if (!Event.get(val)) {
                throw new EventNotFoundException()
            }
        }
        name nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventNameException()
            } else if (val.length() > Constraints.EVENT_NAME_MAX_SIZE) {
                throw new EventNameMaxLengthException()
            }
        }
        description nullable: true, validator: { val, obj ->
            if (val) {
                if (val.length() > Constraints.EVENT_DESCRIPTION_MAX_SIZE) {
                    throw new EventDescriptionMaxLengthException()
                }
            }
        }
        startTime nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventStartTimeException()
            }
        }
        imageName nullable: true
        duration nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEventDurationException()
            }
        }
        genreIds nullable: true, validator: { val, obj ->
            if (!val.findAll().size()) {
                throw new BlankEventGenreException()
            }
        }
    }
}
