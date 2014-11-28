package com.famelive.streamManagement.admin

import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.EventStreamInfo
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.util.DateTimeUtil
import com.famelive.streamManagement.StreamingAPIHelperService
import com.famelive.streamManagement.admin.dto.*
import com.famelive.streamManagement.enums.APIRequestsDetails
import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamInfoRequestCO
import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamInfoResponseCO
import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamStatisticsRequestCO
import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamStatisticsResponseCO
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

/**
 * Created by anil on 21/11/14.
 */
class StreamingAdminService {
    StreamingAPIHelperService streamingAPIHelperService

    List<ChannelInfoDTO> fetchChannels(boolean isOnlyActive = false) {
        List<WowzaChannel> channels = WowzaChannel.findAll { channel ->
            if (isOnlyActive) {
                if (channel.isActive) {
                    return true
                }
            } else {
                return true
            }
        }
        List<ChannelInfoDTO> channelInfoDTOs = []
        channels.each { channel ->
            channelInfoDTOs.add(new ChannelInfoDTO(channel.id))
        }
        return channelInfoDTOs
    }

    List<ChannelInfoDTO> fetchLiveChannels(boolean isOnlyActive = false) {
        List<WowzaChannel> channels = WowzaChannel.findAll { channel ->
            if (!channel.isVODChannel) {
                if (isOnlyActive) {
                    if (channel.isActive) {
                        return true
                    }
                } else {
                    return true
                }
            }
        }
        List<ChannelInfoDTO> channelInfoDTOs = []
        channels.each { channel ->
            channelInfoDTOs.add(new ChannelInfoDTO(channel.id))
        }
        return channelInfoDTOs
    }

    List<ChannelInfoDTO> fetchVODChannels(boolean isOnlyActive = false) {
        List<WowzaChannel> channels = WowzaChannel.findAll { channel ->
            if (channel.isVODChannel) {
                if (isOnlyActive) {
                    if (channel.isActive) {
                        return true
                    }
                } else {
                    return true
                }
            }
        }
        List<ChannelInfoDTO> channelInfoDTOs = []
        channels.each { channel ->
            channelInfoDTOs.add(new ChannelInfoDTO(channel.id))
        }
        return channelInfoDTOs
    }

    ChannelDetailsDTO fetchChannelDetails(long channelId) {
        ChannelDetailsDTO channelDetailsDTO = new ChannelDetailsDTO(channelId)
        Date todayDateTime = new Date()
        Date today = DateTimeUtil.roundOffDayInDate(todayDateTime)
        WowzaChannel channel = WowzaChannel.findById(channelId)
        List<Event> events = Event.findAll("from Event where bookedChannelSlot.channel.id=" + channelId + " and endTime>" + new java.sql.Date(today.getTime()))
        for (Event event : events) {
            if (DateTimeUtil.roundOffDayInDate(event.startTime).compareTo(today) == 0) {
                channelDetailsDTO.todayEventInfoDTOs.add(new EventInfoDTO(event))
            }
            channelDetailsDTO.eventInfoDTOs.add(new EventInfoDTO(event))
        }
        return channelDetailsDTO
    }

    LiveStreamDetailsDTO fetchLiveStreamDetailsForChannel(long channelId) {
        WowzaChannel channel = WowzaChannel.findById(channelId)
        List<EventStreamInfo> streamInfos = EventStreamInfo.currentLiveEventOnChannel(channelId).list()
        EventStreamInfo streamInfo
        if (streamInfos != null && streamInfos.size() > 0) {
            streamInfo = streamInfos.get(0)
        }
        LiveStreamDetailsDTO liveStreamDetailsDTO
        if (streamInfo != null) {
            liveStreamDetailsDTO = new LiveStreamDetailsDTO()
            WowzaFetchInStreamStatisticsRequestCO wowzaFetchInStreamStatisticsRequestCO = new WowzaFetchInStreamStatisticsRequestCO()
            wowzaFetchInStreamStatisticsRequestCO.channelId = channelId
            wowzaFetchInStreamStatisticsRequestCO.applicationName = channel.name
            wowzaFetchInStreamStatisticsRequestCO.streamName = streamInfo.streamName
            wowzaFetchInStreamStatisticsRequestCO.actionName = APIRequestsDetails.FETCH_WOWZA_INCOMING_STREAM_STATISTICS.action
            wowzaFetchInStreamStatisticsRequestCO.init()
            try {
                liveStreamDetailsDTO.inStreamStatisticsDTO = (WowzaFetchInStreamStatisticsResponseCO) streamingAPIHelperService.fetchWowzaIncomingStreamStatistics(wowzaFetchInStreamStatisticsRequestCO)
            } catch (ClassCastException classCastException) {
                println "Unable to fetch InStreamStatistics in StreamingAdminService.fetchLiveStreamDetailsForChannel(), exp:" + classCastException
            }
            WowzaFetchInStreamInfoRequestCO wowzaFetchInStreamInfoRequestCO = new WowzaFetchInStreamInfoRequestCO()
            wowzaFetchInStreamInfoRequestCO.channelId = channelId
            wowzaFetchInStreamInfoRequestCO.applicationName = channel.name
            wowzaFetchInStreamInfoRequestCO.streamName = streamInfo.streamName
            wowzaFetchInStreamInfoRequestCO.actionName = APIRequestsDetails.FETCH_WOWZA_INCOMING_STREAM_INFO.action
            wowzaFetchInStreamInfoRequestCO.init()
            try {
                liveStreamDetailsDTO.inStreamInfoDTO = (WowzaFetchInStreamInfoResponseCO) streamingAPIHelperService.fetchWowzaIncomingStreamInfo(wowzaFetchInStreamInfoRequestCO)
            } catch (ClassCastException classCastException) {
                println "Unable to fetch InStreamInfo in StreamingAdminService.fetchLiveStreamDetailsForChannel(), exp:" + classCastException
            }
            liveStreamDetailsDTO.eventStatus = GrailsHibernateUtil.unwrapIfProxy(streamInfo.event).status as String

            liveStreamDetailsDTO.streamVideoInfoDTO = new StreamVideoInfoDTO()
            liveStreamDetailsDTO.streamVideoInfoDTO.channelInfoDTO = new ChannelInfoDTO(channel)
            liveStreamDetailsDTO.streamVideoInfoDTO.stream = streamInfo.streamName

            liveStreamDetailsDTO.eventInfoDTO = new EventInfoDTO(streamInfo.event)
        }
        return liveStreamDetailsDTO
    }
}
