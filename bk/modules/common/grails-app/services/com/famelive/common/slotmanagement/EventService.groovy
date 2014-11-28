package com.famelive.common.slotmanagement

import com.famelive.common.command.slotmanagement.*
import com.famelive.common.dto.slotmanagement.*
import com.famelive.common.enums.SystemPushNotification
import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.enums.streamManagement.EventURLTypes
import com.famelive.common.exceptions.CommonException
import com.famelive.common.exceptions.slotmanagement.EventStreamInfoNotFoundException
import com.famelive.common.followermanagement.Follow
import com.famelive.common.notification.PushNotificationService
import com.famelive.common.slotManagement.ChannelSlot
import com.famelive.common.slotManagement.Slot
import com.famelive.common.slotManagement.rule.RuleEngine
import com.famelive.common.streamManagement.*
import com.famelive.common.user.User
import com.famelive.common.util.DateTimeUtil
import com.famelive.common.util.FameLiveUtil

import static com.famelive.common.util.PlaceHolder.EVENT_NAME
import static com.famelive.common.util.PlaceHolder.getPopulatedContent;

class EventService {

    def userService
    def commonMailService
    RuleEngine createEventRuleEngine
    RuleEngine editEventRuleEngine
    RuleEngine checkSlotAvailabilityRuleEngine
    SlotService slotService
    CommonStreamManagementService commonStreamManagementService
    PushNotificationService pushNotificationService

    EventDto createEvent(EventCommand eventCommand) throws CommonException {
        eventCommand.validate()
        User user = userService.findUserById(eventCommand.id)
        createEventRuleEngine.applyRules(eventCommand)
        List<ChannelSlot> channelSlots = slotService.fetchAvailableChannelSlots(new Slot(startTime: eventCommand.startTime, endTime: DateTimeUtil.addMinutesToDate(eventCommand.startTime, (int) eventCommand.duration)))
        ChannelSlot recommendedChannelSlot = slotService.fetchRecommendedChannelSlot(channelSlots)
        BookedChannelSlot bookedChannelSlot = slotService.bookChannelSlot(recommendedChannelSlot)

        Event event = new Event(eventCommand, user, bookedChannelSlot).save(failOnError: true, flush: true)
        event.refresh()
        assignGenreToEvent(eventCommand?.genreIds, event)

        commonStreamManagementService.createEventStreamDetails(event)

        commonMailService.sendCreateEventMail(user)
        sendPushNotificationForEventCreation(event)
        addChannelToEvent(event)
        EventDto eventDto = EventDto.createCommonResponseDto(event)
        return eventDto
    }

    void assignGenreToEvent(List<Long> genreIds, Event event) {
        genreIds.each { Long id ->
            Genre genre = Genre.get(id)
            event.addToGenres(genre)
        }
    }

    void sendPushNotificationForEventCreation(Event event) {
        List<String> followerChannels = Follow.getChannelsForPerformer(event.user)
        pushNotificationService.sendDataToRabbidMQ(getPushMessageForEvent(getMessageForEventCreation(event), followerChannels))
    }

    void sendPushNotificationForEventCancel(Event event) {
        List<String> followerChannels = Follow.getChannelsForPerformer(event.user)
        pushNotificationService.sendDataToRabbidMQ(getPushMessageForEvent(getMessageForEventCancel(event), followerChannels))
    }

    void sendPushNotificationForEventEdit(Event event) {
        List<String> followerChannels = Follow.getChannelsForPerformer(event.user)
        pushNotificationService.sendDataToRabbidMQ(getPushMessageForEvent(getMessageForEventEdit(event), followerChannels))
    }

    String getMessageForEventCreation(Event event) {
        Map placeHoldersMap = [:]
        placeHoldersMap[EVENT_NAME] = event?.name
        String html = getPopulatedContent(placeHoldersMap, SystemPushNotification.CREATE_EVENT.displayText)
        return html
    }

    String getMessageForEventCancel(Event event) {
        Map placeHoldersMap = [:]
        placeHoldersMap[EVENT_NAME] = event?.name
        String html = getPopulatedContent(placeHoldersMap, SystemPushNotification.CANCEL_EVENT.displayText)
        return html
    }

    String getMessageForEventEdit(Event event) {
        Map placeHoldersMap = [:]
        placeHoldersMap[EVENT_NAME] = event?.name
        String html = getPopulatedContent(placeHoldersMap, SystemPushNotification.EDIT_EVENT.message)
        return html
    }

    Map getPushMessageForEvent(String message, List<String> channels) {
        Map eventPushMap = [:]
        eventPushMap.message = message
        eventPushMap.channels = channels
        return eventPushMap
    }

    EventDto editEvent(EditEventCommand eventCommand) throws CommonException {
        eventCommand.validate()
        userService.findUserById(eventCommand.id)
        editEventRuleEngine.applyRules(eventCommand)
        Event event = Event.get(eventCommand.eventId)
        updateEvent(event, eventCommand)
        event.refresh()
        removeOldGenre(event)
        assignGenreToEvent(eventCommand?.genreIds, event)
        EventDto eventDto = EventDto.createCommonResponseDto(event)
        sendPushNotificationForEventEdit(event)
        return eventDto
    }

    void removeOldGenre(Event event) {
        event?.genres?.clear()
    }

    void updateEvent(Event event, EditEventCommand eventCommand) {
        event.name = eventCommand.name
        event.description = eventCommand.description

        if (eventCommand.startTime.compareTo(event.startTime) == 0 && eventCommand.endTime.compareTo(event.endTime) == 0) {
            event.save(failOnError: true, flush: true)
        } else {
            event.startTime = eventCommand.startTime
            event.endTime = DateTimeUtil.addMinutesToDate(eventCommand?.startTime, eventCommand.duration as Integer)
            event.duration = eventCommand.duration
            if (slotService.cancelBookedEventChannelSlot(event)) {
                List<ChannelSlot> channelSlots = slotService.fetchAvailableChannelSlots(new Slot(startTime: eventCommand.startTime, endTime: DateTimeUtil.addMinutesToDate(eventCommand.startTime, eventCommand.duration as Integer)))
                ChannelSlot recommendedChannelSlot = slotService.fetchRecommendedChannelSlot(channelSlots)
                BookedChannelSlot bookedChannelSlot = slotService.bookChannelSlot(recommendedChannelSlot)
                event.setBookedChannelSlot(bookedChannelSlot)
                event.save(failOnError: true, flush: true)
                event.refresh()
                commonStreamManagementService.updateEventStreamDetails(event)
            }
        }

    }

    EventImageDto saveEventImage(EventImageCommand eventImageCommand) throws CommonException {
        eventImageCommand.validate()
        userService.findUserById(eventImageCommand.id)
        Event event = Event.get(eventImageCommand?.eventId)
        saveEventImageName(event, eventImageCommand.imageName)
        EventImageDto eventImageDto = EventImageDto.createCommonResponseDto(event)
        return eventImageDto
    }

    void saveEventImageName(Event event, String imageName) {
        event.imageName = imageName
        event.save(flush: true, failOnError: true)
    }

    CancelEventDto cancelEvent(CancelEventCommand cancelEventCommand) throws CommonException {
        cancelEventCommand.validate()
        Event event = Event.get(cancelEventCommand.eventId)
        changeEventStatus(event, EventStatus.CANCELED)
        slotService.cancelBookedEventChannelSlot(event)
        commonStreamManagementService.cancelEventStreamDetails(event)
        commonMailService.sendCancelEventMail(event?.user)
        CancelEventDto cancelEventDto = CancelEventDto.createCommonResponseDto(event)
        sendPushNotificationForEventCancel(event)
        return cancelEventDto
    }

    void changeEventStatus(Event event, EventStatus eventStatus) {
        event.status = eventStatus
        event.save(flush: true, failOnError: true)
    }

    FetchEventsDto fetchAllEventsOfUser(FetchEventsCommand fetchEventsCommand) throws CommonException {
        fetchEventsCommand.validate()
        User user = userService.findUserById(fetchEventsCommand.id)
        List<Event> eventList = Event.findAllByUserAndStatusNotEqual(user, EventStatus.CANCELED, fetchEventsCommand?.properties)
        FetchEventsDto fetchEventsDto = FetchEventsDto.createCommonResponseDto(eventList)
        return fetchEventsDto
    }

    FetchEventsDto fetchAllActiveEvents(FetchEventsCommand fetchEventsCommand) throws CommonException {
        fetchEventsCommand.validate()
        def eventFilteredData = Event.filteredList(fetchEventsCommand)
        FetchEventsDto fetchEventsDto = populateFetchEventDto(fetchEventsCommand, eventFilteredData)
        return fetchEventsDto
    }

    FetchEventsDto fetchAllCancelEvents(FetchEventsCommand fetchEventsCommand) throws CommonException {
        fetchEventsCommand.validate()
        userService.findUserById(fetchEventsCommand.id)
        def eventFilteredData = Event.filteredCancelEventList(fetchEventsCommand)
        FetchEventsDto fetchEventsDto = populateFetchEventDto(fetchEventsCommand, eventFilteredData)
        return fetchEventsDto
    }

    FetchEventsDto populateFetchEventDto(FetchEventsCommand fetchEventsCommand, def eventFilteredData) {
        Integer eventCount = eventFilteredData?.count()
        List<Event> eventList = eventFilteredData.list(fetchEventsCommand?.properties) as List<Event>
        FetchEventsDto fetchEventsDto = FetchEventsDto.createCommonResponseDto(eventList, eventCount)
        return fetchEventsDto
    }

    EventDto fetchEvent(FetchEventCommand fetchEventCommand) throws CommonException {
        fetchEventCommand.validate()
        userService.findUserById(fetchEventCommand.id)
        Event event = Event.get(fetchEventCommand?.eventId)
        EventDto eventDetailDto = EventDto.createCommonResponseDto(event)
        return eventDetailDto
    }

    FetchEventsDto fetchAllEvents(EventListCommand eventListCommand) throws CommonException {
        eventListCommand.validate()
        def eventFilteredData = Event.filteredByStatus(eventListCommand)
        List<Event> eventList = eventFilteredData.list(eventListCommand?.properties) as List<Event>
        FetchEventsDto fetchEventsDto = FetchEventsDto.createCommonResponseDto(eventList)
        return fetchEventsDto
    }

    EventDetailsDto fetchEventDetails(EventDetailsCommand eventDetailsCommand) throws CommonException {
        eventDetailsCommand.validate()
        Event event = Event.findByEventId(eventDetailsCommand.eventID)
        EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(event)
        if (!eventStreamInfo) {
            throw new EventStreamInfoNotFoundException()
        }
        EventDetailsDto eventDetailsDto = EventDetailsDto.createCommonResponseDto(eventStreamInfo)
        return eventDetailsDto
    }

    void checkSlotAvailability(CheckSlotAvailabilityCommand checkSlotAvailabilityCommand) throws CommonException {
        checkSlotAvailabilityCommand.validate()
        checkSlotAvailabilityRuleEngine.applyRules(checkSlotAvailabilityCommand)
    }

    void addChannelToEvent(Event event) {
        event.channels = [FameLiveUtil.randomChannel] as Set<String>
        event.save()
    }

    Map<String, String> fetchEventUrls(EventStreamInfo eventStreamInfo) {
        Map<String, Map> eventUrls = [:]
        Event event = eventStreamInfo.event
        WowzaChannel wowzaChannel = eventStreamInfo.wowzaChannel
        //Event outStream URL's
        Map<Integer, String> eventOutStreamUrls = [:]

        eventOutStreamUrls.put(0, "http://" + wowzaChannel.fetchLiveStreamPath() + "/playlist.m3u8")
        Set<Integer> resolutions = EventStreamResolutions.findByEvent(eventStreamInfo.event)?.resolutions
        resolutions.each { resolution ->
            eventOutStreamUrls.put(resolution, "http://" + wowzaChannel.fetchLiveStreamPath() + "_" + resolution + "p/playlist.m3u8")
        }
        eventUrls.put(EventURLTypes.EVENT_OUT_STREAM_URL, eventOutStreamUrls)

        WowzaChannel linkedVODChannel = wowzaChannel.linkedVODChannel
        if (linkedVODChannel != null && linkedVODChannel.isVODChannel && linkedVODChannel.isActive) {

            Map<Integer, String> eventUpcomingStreamUrls = [:]
            EventUpcomingMediaContent eventUpcomingMediaContent = EventUpcomingMediaContent.findByEvent(event)
            eventUpcomingStreamUrls.put(0, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventUpcomingMediaContent.videoPath + ((eventUpcomingMediaContent.videoExtension != null && eventUpcomingMediaContent.videoExtension.length() > 0) ? ("." + eventUpcomingMediaContent.videoExtension) : "") + "/playlist.m3u8")
            Set<Integer> eventUpcomingStreamResolutions = eventUpcomingMediaContent.videoResolutions
            eventUpcomingStreamResolutions.each { resolution ->
                eventUpcomingStreamUrls.put(resolution, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventUpcomingMediaContent.videoPath + "_" + resolution + "p" + ((eventUpcomingMediaContent.videoExtension != null && eventUpcomingMediaContent.videoExtension.length() > 0) ? ("." + eventUpcomingMediaContent.videoExtension) : "") + "/playlist.m3u8")
            }
            eventUrls.put(EventURLTypes.EVENT_UPCOMING_STREAM_URL, eventUpcomingStreamUrls)

            Map<Integer, String> eventReadyStreamUrls = [:]
            EventReadyMediaContent eventReadyMediaContent = EventReadyMediaContent.findByEvent(event)
            eventReadyStreamUrls.put(0, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventReadyMediaContent.videoPath + ((eventReadyMediaContent.videoExtension != null && eventReadyMediaContent.videoExtension.length() > 0) ? ("." + eventReadyMediaContent.videoExtension) : "") + "/playlist.m3u8")
            Set<Integer> eventReadyStreamResolutions = eventReadyMediaContent.videoResolutions
            eventReadyStreamResolutions.each { resolution ->
                eventReadyStreamUrls.put(resolution, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventReadyMediaContent.videoPath + "_" + resolution + "p" + ((eventReadyMediaContent.videoExtension != null && eventReadyMediaContent.videoExtension.length() > 0) ? ("." + eventReadyMediaContent.videoExtension) : "") + "/playlist.m3u8")
            }
            eventUrls.put(EventURLTypes.EVENT_READY_STREAM_URL, eventReadyStreamUrls)

            Map<Integer, String> eventCompleteStreamUrls = [:]
            EventCompletedMediaContent eventCompleteMediaContent = EventCompletedMediaContent.findByEvent(event)
            eventCompleteStreamUrls.put(0, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventCompleteMediaContent.videoPath + ((eventCompleteMediaContent.videoExtension != null && eventCompleteMediaContent.videoExtension.length() > 0) ? ("." + eventCompleteMediaContent.videoExtension) : "") + "/playlist.m3u8")
            Set<Integer> eventCompleteStreamResolutions = eventCompleteMediaContent.videoResolutions
            eventCompleteStreamResolutions.each { resolution ->
                eventCompleteStreamUrls.put(resolution, "http://" + linkedVODChannel.fetchStreamPath() + "/" + eventCompleteMediaContent.videoPath + "_" + resolution + "p" + ((eventCompleteMediaContent.videoExtension != null && eventCompleteMediaContent.videoExtension.length() > 0) ? ("." + eventCompleteMediaContent.videoExtension) : "") + "/playlist.m3u8")
            }
            eventUrls.put(EventURLTypes.EVENT_COMPLETE_STREAM_URL, eventCompleteStreamUrls)
        }

        return eventUrls
    }
}
