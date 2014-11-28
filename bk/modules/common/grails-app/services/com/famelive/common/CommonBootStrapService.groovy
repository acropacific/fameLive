package com.famelive.common

import com.famelive.common.command.slotmanagement.EventCommand
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.enums.usermanagement.SocialAccount
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.enums.usermanagement.UserRoles
import com.famelive.common.enums.usermanagement.UserType
import com.famelive.common.slotmanagement.*
import com.famelive.common.streamManagement.StreamManagementConstants
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.streamManagement.WowzaChannelServerDetails
import com.famelive.common.user.Role
import com.famelive.common.user.SocialTemplate
import com.famelive.common.user.User
import com.famelive.common.user.UserRole
import com.famelive.common.util.DateTimeUtil
import com.famelive.common.util.slotmanagement.SlotManagementUtil
import com.famelive.common.util.streamManagement.StreamManagementUtil
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.MessageSource

@Transactional
class CommonBootStrapService {

    MessageSource messageSource
    GrailsApplication grailsApplication
    EventService eventService

    void bootstrapUserRoles() {
        UserRoles.values().each { UserRoles role ->
            if (!Role.countByAuthorityIlike(role.value))
                new Role(authority: role.value).save(failOnError: true)
        }
    }

    void bootstrapSuperAdmin() {
        String superAdminUsername = grailsApplication.config.superAdmin.username
        String superAdminPassword = grailsApplication.config.superAdmin.password
        if (!User.countByUsername(superAdminUsername)) {
            User superAdmin = new User(username: superAdminUsername, password: superAdminPassword, email: 'admn.famelive@gmail.com', mobile: '123456', fameName: superAdminUsername).save(failOnError: true)
            User streamingAdmin = new User(username: 'anil', password: 'admin', email: 'anil@gmail.com', mobile: '123456', fameName: 'anil').save(failOnError: true)
            User superAdmin1 = new User(username: "Shalabh Agrawal", password: superAdminPassword, email: 'shalabh@intelligrape.com', mobile: '123456', fameName: "Shalabh").save(failOnError: true)
            User superAdmin3 = new User(username: "Jeevesh Pandey", password: superAdminPassword, email: 'jeevesh@intelligrape.com', mobile: '123456', fameName: "Jeevesh").save(failOnError: true)
            [UserRoles.SUPER_ADMIN, UserRoles.USER].each {
                new UserRole(user: superAdmin, role: Role.findByAuthority(it.value)).save(failOnError: true)
                new UserRole(user: superAdmin1, role: Role.findByAuthority(it.value)).save(failOnError: true)
                new UserRole(user: superAdmin3, role: Role.findByAuthority(it.value)).save(failOnError: true)
                new UserRole(user: streamingAdmin, role: Role.findByAuthority(it.value)).save(failOnError: true)
            }
            new UserRole(user: streamingAdmin, role: Role.findByAuthority(UserRoles.STREAMING_ADMIN.value)).save(failOnError: true)
        }
    }

    void bootstrapUsers() {
        String defaultPassword = grailsApplication.config.user.default.password
        (1..3).each {
            createUser('admin' + it, defaultPassword, UserRoles.ADMIN)
            createUser('watcher' + it, defaultPassword, UserRoles.WATCHER)
        }
        (1..8).each {
            createUser('performer' + it, defaultPassword, UserRoles.PERFORMER, UserType.PERFORMER, UserRegistrationType.FACEBOOK)
        }
    }

    void createUser(String userName, String password, UserRoles role, UserType type = UserType.VIEWER, UserRegistrationType registrationType = UserRegistrationType.MANUAL) {
        if (!User.countByUsername(userName)) {
            try {
                User User = new User(username: userName, password: password, fameName: userName, type: type, email: userName + "@famelive.com", registrationType: registrationType).save(failOnError: true, flush: true)
                new UserRole(user: User, role: Role.findByAuthority(role.value)).save(failOnError: true, flush: true)

            } catch (Exception e) {
                println "Exception occurred when bootstraping user exp:" + e
            }
        }
    }

    void createGenre() {
        ['Music', 'Comedy', 'Health', 'Fashion', 'Educational', 'Astrology', 'Others'].each {
            new Genre(name: it, status: GenreStatus.PUBLISHED, createdBy: User.first()).save(failOnError: true)
        }
    }

    void bootstrapWowzaChannels() {
        String[] wowzaChannels = ["ch1", "ch2", "ch3", "ch4", "ch5", "ch6"]
        List<WowzaChannel> channels = []
        WowzaChannel vodChannel = new WowzaChannel(serverIP: "54.68.108.229", name: "vod", isActive: true, isFullyOccupied: false, filledSpace: 0, isVODChannel: true, liveStreamName: "N/A").save(flush: true, failOnError: true)
        channels.add(vodChannel)
        wowzaChannels.each { channel ->
            channels.add(new WowzaChannel(serverIP: "54.68.108.229", name: channel, isActive: true, isFullyOccupied: false, filledSpace: 0, linkedVODChannel: vodChannel, liveStreamName: "liveStream").save(flush: true, failOnError: true))
        }
        channels.each { channel ->
            new WowzaChannelServerDetails(channel: channel,
                    wowzaAPIVersion: "v2",
                    serverIP: "54.68.108.229",
                    serverPort: "8087",
                    serverName: "WowzaStreamingEngine",
                    vhostName: "_defaultVHost_",
                    apiVersion: "v2",
                    appInstances: "_definst_").save(flush: true, failOnError: true)
        }
    }

    void bootStrapDummyEventData() {
        Genre genre = Genre.findById(1)
        List<EventCommand> eventCommands = []
        EventCommand eventCommand1 = new EventCommand(name: "demo event X01", description: "demo event description X01", startTime: DateTimeUtil.addMinutesToDate(new Date(), 5), duration: 1440, id: User.findByUsername("performer1").id, genreIds: [genre.id])
        eventCommands.add(eventCommand1)

        EventCommand eventCommand2 = new EventCommand(name: "demo event X02", description: "demo event description X02", startTime: DateTimeUtil.addMinutesToDate(new Date(), 10), duration: 1440, id: User.findByUsername("performer2").id, genreIds: [genre.id])
        eventCommands.add(eventCommand2)

        EventCommand eventCommand3 = new EventCommand(name: "demo event X03", description: "demo event description X03", startTime: DateTimeUtil.addMinutesToDate(new Date(), 50), duration: 10, id: User.findByUsername("performer3").id, genreIds: [genre.id])
        eventCommands.add(eventCommand3)

        EventCommand eventCommand4 = new EventCommand(name: "demo event X04", description: "demo event description X04", startTime: DateTimeUtil.addMinutesToDate(new Date() + 30, 15), duration: 10, id: User.findByUsername("performer2").id, genreIds: [genre.id])
        eventCommands.add(eventCommand4)

        EventCommand eventCommand5 = new EventCommand(name: "demo event X05", description: "demo event description X05", startTime: DateTimeUtil.addMinutesToDate(new Date() + 30, 10), duration: 20, id: User.findByUsername("performer3").id, genreIds: [genre.id])
        eventCommands.add(eventCommand5)

        EventCommand eventCommand6 = new EventCommand(name: "demo passed event X06", description: "demo passed event description X06", startTime: DateTimeUtil.addMinutesToDate(new Date() - 1, 10), duration: 20, id: User.findByUsername("performer1").id, genreIds: [genre.id])
        eventCommands.add(eventCommand6)

        eventCommands.each { eventCommand ->
            eventService.createEvent(eventCommand)
        }
    }

    void bootStrapStreamManagementConstants() {
        StreamManagementConstants eventUpcomingVideoPath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_PATH))
        eventUpcomingVideoPath.save(flush: true)
        StreamManagementConstants eventUpcomingVideoExtension = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_EXTENSION, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_EXTENSION))
        eventUpcomingVideoExtension.save(flush: true)
        StreamManagementConstants eventUpcomingVideoPathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_PATH_IS_URL))
        eventUpcomingVideoPathIsUrl.save(flush: true)

        StreamManagementConstants eventReadyVideoPath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH))
        eventReadyVideoPath.save(flush: true)
        StreamManagementConstants eventReadyVideoExtension = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_READY_VIDEO_EXTENSION, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_READY_VIDEO_EXTENSION))
        eventReadyVideoExtension.save(flush: true)
        StreamManagementConstants eventReadyVideoPathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH_IS_URL))
        eventReadyVideoPathIsUrl.save(flush: true)

        StreamManagementConstants eventOfflineVideoPath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH))
        eventOfflineVideoPath.save(flush: true)
        StreamManagementConstants eventOfflineVideoExtension = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_EXTENSION, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_EXTENSION))
        eventOfflineVideoExtension.save(flush: true)
        StreamManagementConstants eventOfflineVideoPathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH_IS_URL))
        eventOfflineVideoPathIsUrl.save(flush: true)

        StreamManagementConstants eventCompleteVideoPath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_PATH))
        eventCompleteVideoPath.save(flush: true)
        StreamManagementConstants eventCompleteVideoExtension = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_EXTENSION, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_EXTENSION))
        eventCompleteVideoExtension.save(flush: true)
        StreamManagementConstants eventCompleteVideoPathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_PATH_IS_URL))
        eventCompleteVideoPathIsUrl.save(flush: true)

        StreamManagementConstants eventUpcomingImagePath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_UPCOMING_IMAGE_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_UPCOMING_IMAGE_PATH))
        eventUpcomingImagePath.save(flush: true)
        StreamManagementConstants eventUpcomingImagePathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_UPCOMING_IMAGE_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_UPCOMING_IMAGE_PATH_IS_URL))
        eventUpcomingImagePathIsUrl.save(flush: true)

        StreamManagementConstants eventReadyImagePath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_READY_IMAGE_PATH))
        eventReadyImagePath.save(flush: true)
        StreamManagementConstants eventReadyImagePathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_READY_IMAGE_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_READY_IMAGE_PATH_IS_URL))
        eventReadyImagePathIsUrl.save(flush: true)

        StreamManagementConstants eventOfflineImagePath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH))
        eventOfflineImagePath.save(flush: true)
        StreamManagementConstants eventOfflineImagePathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH_IS_URL))
        eventOfflineImagePathIsUrl.save(flush: true)

        StreamManagementConstants eventCompleteImagePath = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_COMPLETE_IMAGE_PATH, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_IMAGE_PATH))
        eventCompleteImagePath.save(flush: true)
        StreamManagementConstants eventCompleteImagePathIsUrl = new StreamManagementConstants(constant: StreamManagementConstantKeys.EVENT_COMPLETE_IMAGE_PATH_IS_URL, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_IMAGE_PATH_IS_URL))
        eventCompleteImagePathIsUrl.save(flush: true)

        StreamManagementConstants defaultStreamForFillerVideo = new StreamManagementConstants(constant: StreamManagementConstantKeys.DEFAULT_STREAM_FOR_FILLER_VIDEO, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.DEFAULT_STREAM_FOR_FILLER_VIDEO))
        defaultStreamForFillerVideo.save(flush: true)

        StreamManagementConstants defaultStreamingProtocolForWeb = new StreamManagementConstants(constant: StreamManagementConstantKeys.DEFAULT_STREAMING_PROTOCOL_FOR_WEB, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.DEFAULT_STREAMING_PROTOCOL_FOR_WEB))
        defaultStreamingProtocolForWeb.save(flush: true)

        StreamManagementConstants isDVREnabledForLiveStreams = new StreamManagementConstants(constant: StreamManagementConstantKeys.IS_DVR_ENABLED_FOR_LIVE_STREAMS, value: StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.IS_DVR_ENABLED_FOR_LIVE_STREAMS))
        isDVREnabledForLiveStreams.save(flush: true)
    }

    void bootStrapSlotManagementConstants() {
        SlotManagementConstants eventStartBuffer = new SlotManagementConstants(constant: SlotManagementConstantKeys.EVENT_START_BUFFER, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_START_BUFFER))
        eventStartBuffer.save(flush: true)
        SlotManagementConstants eventEndBuffer = new SlotManagementConstants(constant: SlotManagementConstantKeys.EVENT_END_BUFFER, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_END_BUFFER))
        eventEndBuffer.save(flush: true)
        SlotManagementConstants eventGapTime = new SlotManagementConstants(constant: SlotManagementConstantKeys.EVENT_GAP_TIME, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.EVENT_GAP_TIME))
        eventGapTime.save(flush: true)
        SlotManagementConstants isUserAllowedForReservedSlots = new SlotManagementConstants(constant: SlotManagementConstantKeys.IS_USER_ALLOWED_FOR_RESERVED_SLOTS, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.IS_USER_ALLOWED_FOR_RESERVED_SLOTS))
        isUserAllowedForReservedSlots.save(flush: true)
        SlotManagementConstants isCleanReservedChannelSlotDataOnReinitialization = new SlotManagementConstants(constant: SlotManagementConstantKeys.IS_CLEAN_RESERVED_CHANNEL_SLOT_DATA_ON_REINITIALIZATION, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.IS_CLEAN_RESERVED_CHANNEL_SLOT_DATA_ON_REINITIALIZATION))
        isCleanReservedChannelSlotDataOnReinitialization.save(flush: true)
        SlotManagementConstants allowEventSlotBookingTillDaysFromCurrentDate = new SlotManagementConstants(constant: SlotManagementConstantKeys.ALLOW_EVENT_SLOT_BOOKING_TILL_DAYS_FROM_CURRENT_DATE, value: SlotManagementUtil.fetchSlotManagementConstantDefaultValue(SlotManagementConstantKeys.ALLOW_EVENT_SLOT_BOOKING_TILL_DAYS_FROM_CURRENT_DATE))
        allowEventSlotBookingTillDaysFromCurrentDate.save(flush: true)
    }

    void cleanAndReinitializeSlotManagement() {
        BookedChannelSlot.executeUpdate('delete from BookedChannelSlot')
        AvailableChannelSlot.executeUpdate('delete from AvailableChannelSlot')
        List<WowzaChannel> wowzaChannels = WowzaChannel.findAllByIsVODChannelAndIsActive(false, true)
        wowzaChannels.each { channel ->
            cleanAndReinitializeSlotManagementForChannel(channel)
        }
    }

    void cleanAndReinitializeSlotManagementForChannel(WowzaChannel channel) {
        boolean isCleanReservedChannelSlotDataOnReinitialization = SlotManagementUtil.fetchSlotManagementConstantForChannel(channel, SlotManagementConstantKeys.IS_CLEAN_RESERVED_CHANNEL_SLOT_DATA_ON_REINITIALIZATION)

        if (isCleanReservedChannelSlotDataOnReinitialization) {
            List<ReservedChannelSlot> reservedChannelSlots = ReservedChannelSlot.findAllByChannel(channel)
            reservedChannelSlots.each { reservedChSlot ->
                reservedChSlot.delete(flush: true)
            }
        }

        int allowEventSlotBookingTillDaysFromCurrentDate = SlotManagementUtil.fetchSlotManagementConstantForChannel(channel, SlotManagementConstantKeys.ALLOW_EVENT_SLOT_BOOKING_TILL_DAYS_FROM_CURRENT_DATE)
        //TODO: For testing purpose using available channel slot from previous day, remove it
        Date startTime = DateTimeUtil.roundOffDayInDate(new Date() - 1)
        Date endTime = DateTimeUtil.addDaysToDate(startTime, allowEventSlotBookingTillDaysFromCurrentDate)

        AvailableChannelSlot availableChannelSlot = new AvailableChannelSlot(channel: channel, startTime: startTime, endTime: endTime)
        availableChannelSlot.save(flush: true)

    }

    void createSocialAccountTemplate() {
        SocialAccount.values().each { SocialAccount socialAccount ->
            new SocialTemplate(socialAccount: socialAccount, message: "${socialAccount} template").save(failOnError: true)
        }
    }
}
