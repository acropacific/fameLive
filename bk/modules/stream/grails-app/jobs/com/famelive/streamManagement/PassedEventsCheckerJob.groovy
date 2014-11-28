package com.famelive.streamManagement

import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.DateTimeUtil
import org.codehaus.groovy.grails.commons.GrailsApplication


class PassedEventsCheckerJob {

    GrailsApplication grailsApplication
    int securityTokenEndBufferTime = 10 //grailsApplication.config.famelive.event.securityToken.afterEndBufferTime
    SchedulerHelperService schedulerHelperService
    static triggers = {
        simple repeatInterval: 60000l // execute job once in 60 seconds
    }

    def execute() {
        // execute job
        //checkPassedEvents()
    }

    void checkPassedEvents() {
        List<Event> passedEvents = Event.findAll {
            ((status == EventStatus.READY || status == EventStatus.ON_AIR) && endTime <= DateTimeUtil.subtractMinutesFromDate(new Date(), securityTokenEndBufferTime))
        }
        passedEvents.each { passedEvent ->
            schedulerHelperService.changeEventStateToPassed(passedEvent)
        }
    }
}
