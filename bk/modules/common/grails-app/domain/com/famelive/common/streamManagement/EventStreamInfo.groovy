package com.famelive.common.streamManagement

import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.enums.streamManagement.StreamingProtocols
import com.famelive.common.slotmanagement.Event
import com.famelive.common.util.DateTimeUtil
import com.famelive.common.util.streamManagement.StreamManagementUtil

class EventStreamInfo {

    static constraints = {
        event unique: true
        streamName nullable: true
    }
    Event event
    WowzaChannel wowzaChannel
    String streamName

    public void setEvent(Event event) {
        this.event = event
        this.streamName = event.eventId
    }
    static namedQueries = {
        currentLiveEvents {
            "event" {
                lte("startTime", DateTimeUtil.currentDate)
                gt("endTime", DateTimeUtil.currentDate)
            }
        }
        currentLiveEventOnChannel { wowzaChannelId ->

            "event" {
                lte("startTime", DateTimeUtil.currentDate)
                gt("endTime", DateTimeUtil.currentDate)
            }
            "wowzaChannel" {
                eq("id", wowzaChannelId)
            }
        }
    }

    String fetchStreamUrl(StreamingProtocols streamingProtocol, boolean isDVRStream = false) {
        String streamUrl = ""
        if (isDVRStream) {
            streamUrl = "?DVR"
        }
        switch (streamingProtocol) {
            case StreamingProtocols.HLS:
                streamUrl = "http://" + wowzaChannel.fetchStreamPath() + "/" + streamName + "/playlist.m3u8" + streamUrl;
                break;
            case StreamingProtocols.RTMP:
                streamUrl = "rtmp://" + wowzaChannel.fetchStreamPath() + "/" + streamName + streamUrl;
                break;
            case StreamingProtocols.RTSP:
                streamUrl = "rtsp://" + wowzaChannel.fetchStreamPath() + "/" + streamName + streamUrl;
                break;
            default:
                StreamingProtocols defaultProtocol = StreamingProtocols.valueOf(((String) StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.DEFAULT_STREAMING_PROTOCOL_FOR_WEB)).toLowerCase())
                streamUrl = fetchStreamUrl(defaultProtocol,
                        StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.IS_DVR_ENABLED_FOR_LIVE_STREAMS))
                break;
        }
        return streamUrl
    }

    String fetchLiveStreamUrl(StreamingProtocols streamingProtocol, boolean isDVRStream = false) {
        String streamUrl = ""
        if (isDVRStream) {
            streamUrl = "?DVR"
        }
        switch (streamingProtocol) {
            case StreamingProtocols.HLS:
                streamUrl = "http://" + wowzaChannel.fetchLiveStreamPath() + "/playlist.m3u8" + streamUrl;
                break;
            case StreamingProtocols.RTMP:
                streamUrl = "rtmp://" + wowzaChannel.fetchLiveStreamPath() + streamUrl;
                break;
            case StreamingProtocols.RTSP:
                streamUrl = "rtsp://" + wowzaChannel.fetchLiveStreamPath() + streamUrl;
                break;
            default:
                StreamingProtocols defaultProtocol = StreamingProtocols.valueOf(((String) StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.DEFAULT_STREAMING_PROTOCOL_FOR_WEB)).toLowerCase())
                streamUrl = fetchStreamUrl(defaultProtocol,
                        StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.IS_DVR_ENABLED_FOR_LIVE_STREAMS))
                break;
        }
        return streamUrl
    }
}
