package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 26/11/14.
 */
class WowzaDoActionOnInStreamRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.PUT
        contentType = "text/html"
    }

    String applicationName
    String streamName
    String inStreamAction       //resetStream/disconnectStream

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/instances/" + appInstances + "/incomingstreams/" + streamName + "/actions/" + inStreamAction
    }
}
