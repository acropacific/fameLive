package com.famelive.streamManagement.admin.dto

import com.famelive.common.streamManagement.WowzaChannel

/**
 * Created by anil on 21/11/14.
 */
class ChannelInfoDTO {
    long channelId
    String name
    String serverIP
    String serverStreamingPort = "1935"
    ChannelInfoDTO linkedVODChannel
    boolean isActive = true
    boolean isVODChannel = false
    String channelAlias
    String streamPath
    String liveStreamPath
    String liveStreamName

    ChannelInfoDTO(long channelId) {
        this(WowzaChannel.findById(channelId))
    }

    ChannelInfoDTO(WowzaChannel channel) {
        this.name = channel.getName()
        this.serverIP = channel.serverIP
        this.serverStreamingPort = channel.serverStreamingPort
        if (channel.linkedVODChannel != null) {
            this.linkedVODChannel = new ChannelInfoDTO(channel.linkedVODChannel?.id)
        }
        this.isActive = channel.isActive
        this.isVODChannel = channel.isVODChannel
        this.channelAlias = fetchChannelAlias()
        this.channelId = channel.id
        this.liveStreamName = channel.liveStreamName
        this.streamPath = channel.fetchStreamPath()
        this.liveStreamPath = channel.fetchLiveStreamPath()
    }

    String fetchChannelAlias() {
        return name.toUpperCase() + " On " + serverIP
    }
}
