package com.famelive.streamManagement

import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.DateTimeUtil
import org.codehaus.groovy.grails.commons.GrailsApplication

class ReadyEventsCheckerJob {
    Date currentDateTime
    GrailsApplication grailsApplication
    SchedulerHelperService schedulerHelperService
    int securityTokenStartBufferTime = 15  //grailsApplication.config.famelive.event.securityToken.beforeStartBufferTime

    static triggers = {
//        simple repeatInterval: 1000l * 60l *1l Long.parseLong(grailsApplication.config.mediaPlan.scheduler.stateChanger.timeInterval)
        simple repeatInterval: 60000l // execute job once in 60 seconds
    }

    def execute() {
        currentDateTime = new Date()
//        checkReadyEvents();
    }

    void checkReadyEvents() {
        List<Event> readyEvents = Event.findAll {
            (status == EventStatus.UP_COMING && startTime <= DateTimeUtil.addMinutesToDate(new Date(), securityTokenStartBufferTime))
        }
        readyEvents.each { readyEvent ->
            schedulerHelperService.changeEventStateToReady(readyEvent)
        }
    }


}
