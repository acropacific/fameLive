package com.famelive.streamManagement.admin.dto

/**
 * Created by anil on 27/11/14.
 */
class StreamVideoInfoDTO {
    String name
    String extension
    String url
    String stream
    ChannelInfoDTO channelInfoDTO

    String fetchHLSStreamUrl() {
        return "http://" + channelInfoDTO?.streamPath + "/" + stream + "/playlist.m3u8"
    }

    String fetchRTMPStreamUrl() {
        return "rtmp://" + channelInfoDTO?.streamPath + "/" + stream
    }
}
