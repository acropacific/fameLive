package com.famelive.streamManagement

import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.*
import com.famelive.common.streamManagement.historyDomains.*
import com.famelive.streamManagement.command.ChangeEventStateRequestCO
import com.famelive.streamManagement.wowza.Publisher
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class SchedulerHelperService {

    APIV1Service streamAPIV1Service
    StreamingAPIHelperService streamingAPIHelperService

    void addInStreamPublisherForEvent(Event readyEvent, WowzaChannel wowzaChannel) {
        EventInStreamCredentials eventInStreamCredentials = EventInStreamCredentials.findByEvent(readyEvent)
        if (eventInStreamCredentials == null) {
            Publisher inStreamPublisher = addPublisher(wowzaChannel)
            if (inStreamPublisher != null) {
                eventInStreamCredentials = new EventInStreamCredentials(username: inStreamPublisher.username, password: inStreamPublisher.password, event: readyEvent, validFrom: readyEvent.startTime, validTill: readyEvent.endTime)
                eventInStreamCredentials.save(failOnError: true, flush: true)
            } else {
                throw new RuntimeException("In Stream Publisher object is null")
            }
        }
    }

    void addOutStreamPublisherForEvent(Event readyEvent, WowzaChannel wowzaChannel) {
        EventOutStreamCredentials eventOutStreamCredentials = EventOutStreamCredentials.findByEvent(readyEvent)
        if (eventOutStreamCredentials == null) {
            Publisher outStreamPublisher = addPublisher(wowzaChannel)
            if (outStreamPublisher != null) {
                eventOutStreamCredentials = new EventOutStreamCredentials(username: outStreamPublisher.username, password: outStreamPublisher.password, event: readyEvent, validFrom: readyEvent.startTime, validTill: readyEvent.endTime)
                eventOutStreamCredentials.save(failOnError: true, flush: true)
            } else {
                throw new RuntimeException("Out Stream Publisher object is null")
            }
        }
    }

    boolean changeEventStateToReady(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        try {
            Event readyEvent = Event.findByEventId(changeEventStateRequestCO.eventID)
            if (readyEvent.status == EventStatus.UP_COMING) {

                EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(readyEvent)
                WowzaChannel wowzaChannel = eventStreamInfo.wowzaChannel

                addInStreamPublisherForEvent(readyEvent, wowzaChannel)
                addOutStreamPublisherForEvent(readyEvent, wowzaChannel)

                EventInStreamStatus eventInStreamStatus = EventInStreamStatus.findByEvent(readyEvent)
                if (eventInStreamStatus == null) {
                    eventInStreamStatus = new EventInStreamStatus(event: readyEvent, status: EventStatus.READY.value, clientIP: changeEventStateRequestCO.clientIP)
                } else {
                    eventInStreamStatus.setStatus(EventStatus.READY.value)
                    eventInStreamStatus.setClientIP(changeEventStateRequestCO.clientIP)
                }
                eventInStreamStatus.save(failOnError: true, flush: true)

                EventOutStreamStatus eventOutStreamStatus = EventOutStreamStatus.findByEvent(readyEvent)
                if (eventOutStreamStatus == null) {
                    eventOutStreamStatus = new EventOutStreamStatus(event: readyEvent, status: EventStatus.READY.value)
                } else {
                    eventOutStreamStatus.setStatus(EventStatus.READY.value)
                }
                eventOutStreamStatus.save(failOnError: true, flush: true)

                readyEvent.setStatus(EventStatus.READY)
                readyEvent.save(failOnError: true, flush: true)

                streamingAPIHelperService.updateWowzaInputStreamFillerVideoDetail(changeEventStateRequestCO)

                isStateChanged = true
            } else {
                isStateChanged = false
            }
        } catch (Exception e) {
            println("Exception occurred in SchedulerHelperService.changeEventStateToReady() exp:" + e)
            isStateChanged = false
        }

        return isStateChanged
    }


    Publisher addPublisher(WowzaChannel wowzaChannel) {
        Publisher publisher = new Publisher()
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("applicationName", wowzaChannel.name)
        jsonObject.put("publisherName", publisher.username)
        jsonObject.put("publisherPassword", publisher.password)
        jsonObject.put("publisherDescription", publisher.description)
        jsonObject.put("serverIP", wowzaChannel.serverIP)
        if (streamAPIV1Service.addWowzaPublisher(jsonObject).success) {
            return publisher
        }
    }

    boolean removePublisher(EventStreamInfo eventStreamInfo, EventStreamCredentials eventStreamCredentials) {
        boolean isPublisherRemoved
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("applicationName", eventStreamInfo.wowzaChannel.name)
        jsonObject.put("publisherName", eventStreamCredentials.username)
        jsonObject.put("serverIP", eventStreamInfo.wowzaChannel.serverIP)
        if (streamAPIV1Service.removeWowzaPublisher(jsonObject).success) {
            isPublisherRemoved = true
        }
        return isPublisherRemoved
    }

    boolean changeEventStateToPassed(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        try {
            Event passedEvent = Event.findByEventId(changeEventStateRequestCO.eventID)
            if (new Date() >= passedEvent.endTime) {
                EventStatus eventsPreviousStatus = passedEvent.status

                EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(passedEvent)

                EventInStreamCredentials eventInStreamCredentials = EventInStreamCredentials.findByEvent(passedEvent)
                if (eventInStreamCredentials != null) {
                    EventInStreamCredentialsHistory eventInStreamCredentialsHistory = new EventInStreamCredentialsHistory(eventInStreamCredentials.properties)
                    eventInStreamCredentialsHistory.save(failOnError: true, flush: true)
                    eventInStreamCredentials.delete(flush: true)
                    removePublisher(eventStreamInfo, eventInStreamCredentials);
                }



                EventInStreamStatus eventInStreamStatus = EventInStreamStatus.findByEvent(passedEvent)
                if (eventInStreamStatus != null) {
                    eventInStreamStatus.status = EventStatus.COMPLETED.value
                    EventInStreamStatusHistory eventInStreamStatusHistory = new EventInStreamStatusHistory(eventInStreamStatus.properties)
                    eventInStreamStatusHistory.save(failOnError: true, flush: true)
                    eventInStreamStatus.delete(flush: true)
                }

                EventOutStreamCredentials eventOutStreamCredentials = EventOutStreamCredentials.findByEvent(passedEvent)
                if (eventOutStreamCredentials != null) {
                    EventOutStreamCredentialsHistory eventOutStreamCredentialsHistory = new EventOutStreamCredentialsHistory(eventOutStreamCredentials.properties)
                    eventOutStreamCredentialsHistory.save(failOnError: true, flush: true)
                    eventOutStreamCredentials.delete(flush: true)
                    removePublisher(eventStreamInfo, eventOutStreamCredentials);
                }

                EventOutStreamStatus eventOutStreamStatus = EventOutStreamStatus.findByEvent(passedEvent)
                if (eventOutStreamStatus != null) {
                    eventOutStreamStatus.status = EventStatus.COMPLETED.value
                    EventOutStreamStatusHistory eventOutStreamStatusHistory = new EventOutStreamStatusHistory(eventOutStreamStatus.properties)
                    eventOutStreamStatusHistory.save(failOnError: true, flush: true)
                    eventOutStreamStatus.delete(flush: true)
                }

                /*EventHistory eventHistory = new EventHistory(passedEvent.properties)
                eventHistory.save(failOnError: true, flush: true)
                passedEvent.delete(flush: true)*/

                if (eventsPreviousStatus != EventStatus.READY && eventsPreviousStatus != EventStatus.UP_COMING) {
                    //EventVideoFileInfo eventVideoFileInfo = new EventVideoFileInfo(event: eventHistory, serverIP: eventStreamInfo.wowzaChannel.serverIP, fileName: eventStreamInfo.streamName + ".mp4", videoResolution: 480, parentDirectory: "/usr/local/WowzaStreamingEngine/content/")
                    EventVideoFileInfo eventVideoFileInfo = new EventVideoFileInfo(event: passedEvent, serverIP: eventStreamInfo.wowzaChannel.serverIP, fileName: eventStreamInfo.streamName + ".mp4", videoResolution: 480, parentDirectory: "/usr/local/WowzaStreamingEngine/content/")
                    eventVideoFileInfo.save(failOnError: true, flush: true)
                }

                passedEvent.setStatus(EventStatus.COMPLETED)
                passedEvent.save(failOnError: true, flush: true)

                //Remove filler video info and set default filler video for default stream
                streamingAPIHelperService.resetWowzaInputStreamFillerVideoDetail(changeEventStateRequestCO)

                if (eventStreamInfo != null) {
                    EventStreamInfoHistory eventStreamInfoHistory = new EventStreamInfoHistory(eventStreamInfo.properties)
                    eventStreamInfoHistory.save(failOnError: true, flush: true)
                    eventStreamInfo.delete(flush: true)
                }

                isStateChanged = true
            } else {
                isStateChanged = false
            }
        } catch (Exception e) {
            println("Exception occurred in SchedulerHelperService.changeEventStateToPassed() exp:" + e)
            isStateChanged = false
        }
        return isStateChanged
    }

    boolean changeEventStateToPaused(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        Event pausedEvent = Event.findByEventId(changeEventStateRequestCO.eventID)
        if (pausedEvent.status == EventStatus.ON_AIR) {
            EventInStreamStatus eventInStreamStatus = EventInStreamStatus.findByEvent(pausedEvent)
            if (eventInStreamStatus != null) {
                eventInStreamStatus.status = EventStatus.PAUSED.value
                eventInStreamStatus.save(failOnError: true, flush: true)
                isStateChanged = true
            } else {
                isStateChanged = false
            }
            EventOutStreamStatus eventOutStreamStatus = EventOutStreamStatus.findByEvent(pausedEvent)
            if (isStateChanged && eventOutStreamStatus != null) {
                eventOutStreamStatus.status = EventStatus.PAUSED.value
                eventOutStreamStatus.save(failOnError: true, flush: true)
            } else {
                isStateChanged = false
            }
            if (isStateChanged) {
                pausedEvent.setStatus(EventStatus.PAUSED)
                pausedEvent.save(failOnError: true, flush: true)
            } else {
                isStateChanged = false
            }
        } else {
            isStateChanged = false
        }
        return isStateChanged
    }

    boolean changeEventStateToLive(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        try {
            Event liveEvent = Event.findByEventId(changeEventStateRequestCO.eventID)
            if (new Date() >= liveEvent.startTime) {

                EventInStreamStatus eventInStreamStatus = EventInStreamStatus.findByEvent(liveEvent)
                if (eventInStreamStatus != null) {
                    eventInStreamStatus.status = EventStatus.ON_AIR.value
                    eventInStreamStatus.save(failOnError: true, flush: true)
                }

                EventOutStreamStatus eventOutStreamStatus = EventOutStreamStatus.findByEvent(liveEvent)
                if (eventOutStreamStatus != null) {
                    eventOutStreamStatus.status = EventStatus.ON_AIR.value
                    eventOutStreamStatus.save(failOnError: true, flush: true)
                }

                liveEvent.setStatus(EventStatus.ON_AIR)
                liveEvent.save(failOnError: true, flush: true)

                isStateChanged = true
            } else {
                isStateChanged = false
            }
        } catch (Exception e) {
            println("Exception occurred in SchedulerHelperService.changeEventStateToLive() exp:" + e)
            isStateChanged = false
        }
        return isStateChanged
    }
}
