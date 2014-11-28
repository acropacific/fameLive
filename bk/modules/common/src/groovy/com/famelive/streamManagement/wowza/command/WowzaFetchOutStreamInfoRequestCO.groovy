package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 26/11/14.
 */
class WowzaFetchOutStreamInfoRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.GET
        contentType = "text/html"
    }

    String applicationName
    String streamName

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/instances/" + appInstances + "/outgoingstreams/" + streamName
    }
}
