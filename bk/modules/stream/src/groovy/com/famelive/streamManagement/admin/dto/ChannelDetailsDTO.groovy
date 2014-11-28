package com.famelive.streamManagement.admin.dto

import com.famelive.common.enums.streamManagement.FillerMediaContentTypes
import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.util.streamManagement.StreamManagementUtil

/**
 * Created by anil on 21/11/14.
 */
class ChannelDetailsDTO {
    ChannelInfoDTO channelInfoDTO
    List<EventInfoDTO> eventInfoDTOs = []
    List<EventInfoDTO> todayEventInfoDTOs = []
    Map<FillerMediaContentTypes, FillerVideoInfoDTODTO> fillerVideos = [:]

    ChannelDetailsDTO(long channelId) {
        this(WowzaChannel.findById(channelId))
    }

    ChannelDetailsDTO(WowzaChannel channel) {
        this.channelInfoDTO = new ChannelInfoDTO(channel)
        fetchFillerVideoInfo(channel)
    }

    private void fetchFillerVideoInfo(WowzaChannel channel) {
        String eventCompleteVideo = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_PATH)
        String eventCompleteVideoExt = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_EXTENSION)

        FillerVideoInfoDTODTO eventCompleteFillerVideoInfo = new FillerVideoInfoDTODTO(name: eventCompleteVideo, extension: eventCompleteVideoExt, stream: eventCompleteVideo)
        if (eventCompleteVideoExt != null && eventCompleteVideoExt.length() > 1) {
            eventCompleteFillerVideoInfo.stream = eventCompleteVideo + "." + eventCompleteVideoExt
        }

        String eventReadyVideo = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_READY_VIDEO_PATH)
        String eventReadyVideoExt = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_READY_VIDEO_EXTENSION)
        FillerVideoInfoDTODTO eventReadyFillerVideoInfo = new FillerVideoInfoDTODTO(name: eventReadyVideo, extension: eventReadyVideoExt, stream: eventReadyVideo)
        if (eventReadyVideoExt != null && eventReadyVideoExt.length() > 1) {
            eventReadyFillerVideoInfo.stream = eventReadyVideo + "." + eventReadyVideoExt
        }

        String eventUpcomingVideo = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_PATH)
        String eventUpcomingVideoExt = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_UPCOMING_VIDEO_EXTENSION)
        FillerVideoInfoDTODTO eventUpcomingFillerVideoInfo = new FillerVideoInfoDTODTO(name: eventUpcomingVideo, extension: eventUpcomingVideoExt, stream: eventUpcomingVideo)
        if (eventUpcomingVideoExt != null && eventUpcomingVideoExt.length() > 1) {
            eventUpcomingFillerVideoInfo.stream = eventUpcomingVideo + "." + eventUpcomingVideoExt
        }

        String eventOfflineVideo = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH)
        String eventOfflineVideoExt = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_EXTENSION)
        FillerVideoInfoDTODTO eventOfflineFillerVideoInfo = new FillerVideoInfoDTODTO(name: eventOfflineVideo, extension: eventOfflineVideoExt, stream: eventOfflineVideo)
        if (eventOfflineVideoExt != null && eventOfflineVideoExt.length() > 1) {
            eventOfflineFillerVideoInfo.stream = eventOfflineVideo + "." + eventOfflineVideoExt
        }

        eventCompleteFillerVideoInfo.channelInfoDTO = channelInfoDTO.linkedVODChannel
        eventOfflineFillerVideoInfo.channelInfoDTO = channelInfoDTO.linkedVODChannel
        eventUpcomingFillerVideoInfo.channelInfoDTO = channelInfoDTO.linkedVODChannel
        eventReadyFillerVideoInfo.channelInfoDTO = channelInfoDTO.linkedVODChannel

        fillerVideos.put(FillerMediaContentTypes.EVENT_COMPLETE_FILLER_VIDEO, eventCompleteFillerVideoInfo)
        fillerVideos.put(FillerMediaContentTypes.EVENT_READY_FILLER_VIDEO, eventReadyFillerVideoInfo)
        fillerVideos.put(FillerMediaContentTypes.EVENT_OFFLINE_FILLER_VIDEO, eventOfflineFillerVideoInfo)
        fillerVideos.put(FillerMediaContentTypes.EVENT_UPCOMING_FILLER_VIDEO, eventUpcomingFillerVideoInfo)
    }
}
