package com.famelive.common.streamManagement

class WowzaChannelServerDetails {

    WowzaChannel channel

    String wowzaAPIVersion
    String serverIP
    String serverPort
    String serverName
    String vhostName
    String apiVersion
    String appInstances

    static constraints = {
        channel unique: true
    }
}
