package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 29/10/14.
 */
class WowzaRemovePublisherRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.DELETE
        contentType = "text/html"
    }
    String applicationName
    String publisherName

    String getURL() {
        return "http://" + serverIP + ":" + serverPort + "/" + wowzaAPIVersion + "/servers/" + serverName + "/vhosts/" + vhostName + "/applications/" + applicationName + "/publishers/" + publisherName
    }

}
