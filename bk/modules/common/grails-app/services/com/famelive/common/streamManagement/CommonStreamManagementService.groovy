package com.famelive.common.streamManagement

import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.historyDomains.*
import grails.transaction.Transactional

@Transactional
class CommonStreamManagementService {

    def createEventStreamDetails(Event event) {
        EventStreamInfo eventStreamInfo = new EventStreamInfo(event: event, wowzaChannel: event.bookedChannelSlot.channel, streamName: event.eventId)
        eventStreamInfo.save(failOnError: true, flush: true)

        //TODO : implement mediaContent upload functionality and overwrite default values
        EventCompletedMediaContent eventCompletedMediaContent = new EventCompletedMediaContent(event: event)
        eventCompletedMediaContent.setDefaultValues()
        eventCompletedMediaContent.save(failOnError: true, flush: true)
        EventReadyMediaContent eventReadyMediaContent = new EventReadyMediaContent(event: event)
        eventReadyMediaContent.setDefaultValues()
        eventReadyMediaContent.save(failOnError: true, flush: true)
        EventUpcomingMediaContent eventUpcomingMediaContent = new EventUpcomingMediaContent(event: event)
        eventUpcomingMediaContent.setDefaultValues()
        eventUpcomingMediaContent.save(failOnError: true, flush: true)
        EventOfflineMediaContent eventOfflineMediaContent = new EventOfflineMediaContent(event: event)
        eventOfflineMediaContent.setDefaultValues()
        eventOfflineMediaContent.save(failOnError: true, flush: true)

        createEventStreamResolutions(event)
    }

    def updateEventStreamDetails(Event event) {
        EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(event)
        eventStreamInfo.setWowzaChannel(event.bookedChannelSlot.channel)
        eventStreamInfo.save(failOnError: true, flush: true)

        updateEventStreamResolutions(event)
    }

    def cancelEventStreamDetails(Event event) {
        EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(event)
        EventStreamInfoHistory eventStreamInfoHistory = new EventStreamInfoHistory(eventStreamInfo.properties)
        eventStreamInfoHistory.save(failOnError: true, flush: true)
        eventStreamInfo.delete(flush: true)

        EventCompletedMediaContent eventCompletedMediaContent = EventCompletedMediaContent.findByEvent(event)
        EventCompletedMediaContentHistory eventCompletedMediaContentHistory = new EventCompletedMediaContentHistory(eventCompletedMediaContent.properties)
        eventCompletedMediaContentHistory.save(failOnError: true, flush: true)
        eventCompletedMediaContent.delete(flush: true)

        EventReadyMediaContent eventReadyMediaContent = EventReadyMediaContent.findByEvent(event)
        EventReadyMediaContentHistory eventReadyMediaContentHistory = new EventReadyMediaContentHistory(eventReadyMediaContent.properties)
        eventReadyMediaContentHistory.save(failOnError: true, flush: true)
        eventReadyMediaContent.delete(flush: true)

        EventOfflineMediaContent eventOfflineMediaContent = EventOfflineMediaContent.findByEvent(event)
        EventOfflineMediaContentHistory eventOfflineMediaContentHistory = new EventOfflineMediaContentHistory(eventOfflineMediaContent.properties)
        eventOfflineMediaContentHistory.save(failOnError: true, flush: true)
        eventOfflineMediaContent.delete(flush: true)

        EventUpcomingMediaContent eventUpcomingMediaContent = EventUpcomingMediaContent.findByEvent(event)
        EventUpcomingMediaContentHistory eventUpcomingMediaContentHistory = new EventUpcomingMediaContentHistory(eventUpcomingMediaContent.properties)
        eventUpcomingMediaContentHistory.save(failOnError: true, flush: true)
        eventUpcomingMediaContent.delete(flush: true)


        event.setIsHistory(true)
        event.save(failOnError: true, flush: true)
    }

    def updateEventStreamResolutions(Event event) {
        //Do nothing, assuming same resolutions for new BooKedSlot as previous one
    }

    def createEventStreamResolutions(Event event) {
        //TODO : fetch the available out stream resolutions from wowza server and set the appropriate values
        EventStreamResolutions eventStreamResolutions = new EventStreamResolutions(event: event)
        eventStreamResolutions.addToResolutions(160)
        eventStreamResolutions.addToResolutions(360)
        eventStreamResolutions.save(flush: true)

        /*InputStream inputStream
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        int responseCode = requestCallBroker.execute(wowzaGetApplicationsRequestCO, inputStream)
        ResponseCO responseCO = new ResponseCO()
        if (responseCode == 200) {
            if (inputStream) {
                WowzaGetApplicationsResponseCO apps = new WowzaGetApplicationsResponseCO(inputStream)
                responseCO.success = true
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("streamManagement.getWowzaApplications.success")
                println("total apps :" + apps.applications.size())
                println(apps.applications[0].id + ":" + apps.applications[0].appType + ":" + apps.applications[0].href)
            } else {
                responseCO.success = false
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("streamManagement.getWowzaApplications.failed.noReadableResponseFromServer")

            }
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("streamManagement.getWowzaApplications.failed.invalidServerResponse")
        }
        return responseCO*/
    }
}
