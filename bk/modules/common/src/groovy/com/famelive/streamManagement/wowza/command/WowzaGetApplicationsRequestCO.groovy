package com.famelive.streamManagement.wowza.command

import com.famelive.common.enums.RequestType

/**
 * Created by anil on 27/10/14.
 */
class WowzaGetApplicationsRequestCO extends WowzaRequestCO {
    {
        requestMethod = RequestType.GET
        contentType = "text/html"
    }

    String getURL() {
        return super.getURL()
    }
}
