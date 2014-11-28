package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType
import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.streamManagement.WowzaChannelServerDetails
import com.famelive.streamManagement.command.RequestCO
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

/**
 * Created by anil on 27/10/14.
 */
abstract class WowzaRequestCO extends RequestCO {

    {
        requestMethod = RequestType.POST
        wowzaAPIVersion = "v2"
        serverIP = "54.68.108.229"
        serverPort = "8087"
        serverName = "WowzaStreamingEngine"
        vhostName = "_defaultVHost_"
        apiVersion = "v2"
        appInstances = "_definst_"
    }
    String wowzaAPIVersion
    String serverIP
    String serverPort
    String serverName
    String vhostName
    String apiVersion
    String appInstances
    long eventId = -1
    long channelId = -1

    void init() {
        WowzaChannel channel
        if (channelId != -1) {
            channel = WowzaChannel.findById(channelId)
        } else if (eventId != -1) {
            channel = GrailsHibernateUtil.unwrapIfProxy(Event.findById(eventId).bookedChannelSlot).channel
        }
        WowzaChannelServerDetails channelServerDetails = WowzaChannelServerDetails.findByChannel(channel)
        if (channelServerDetails != null) {
            wowzaAPIVersion = channelServerDetails.wowzaAPIVersion
            serverIP = channelServerDetails.serverIP
            serverPort = channelServerDetails.serverPort
            serverName = channelServerDetails.serverName
            vhostName = channelServerDetails.vhostName
            apiVersion = channelServerDetails.apiVersion
            appInstances = channelServerDetails.appInstances
        }
    }

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications"
    }

    String getBody() {
        return ""
    }
}
