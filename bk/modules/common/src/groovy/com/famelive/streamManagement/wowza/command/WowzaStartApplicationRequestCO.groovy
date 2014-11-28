package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 17/11/14.
 */
class WowzaStartApplicationRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.PUT
        contentType = "text/html"
    }
    String applicationName
    final String action = "start"

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/actions/" + action
    }
}
