package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 17/11/14.
 */
class WowzaFetchAdvanceApplicationConfigurationRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.GET
        contentType = "text/html"
    }
    String applicationName

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/adv"
    }
}
