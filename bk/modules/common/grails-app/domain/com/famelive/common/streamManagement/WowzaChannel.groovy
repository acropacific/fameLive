package com.famelive.common.streamManagement

class WowzaChannel {

    static constraints = {
        linkedVODChannel nullable: true
    }

    String name
    String serverIP
    String serverStreamingPort = "1935"
    WowzaChannel linkedVODChannel
    boolean isActive = true
    boolean isVODChannel = false
    boolean isFullyOccupied = false
    float filledSpace = 0
    Date dateCreated = new Date()
    Date dateModified = new Date()
    String liveStreamName = ""

    String fetchStreamPath() {
        return serverIP + ":" + serverStreamingPort + "/" + name
    }

    String fetchLinkedChannelStreamPath() {
        if (linkedVODChannel != null) {
            return linkedVODChannel.fetchStreamPath()
        }
        return null
    }

    String fetchLiveStreamPath() {
        return serverIP + ":" + serverStreamingPort + "/" + name + "/" + liveStreamName
    }


}
