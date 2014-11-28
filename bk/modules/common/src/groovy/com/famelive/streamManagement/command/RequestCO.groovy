package com.famelive.streamManagement.command

import com.famelive.common.enums.RequestType
import grails.validation.Validateable

/**
 * Created by anil on 16/10/14.
 */

@Validateable
class RequestCO {
    String actionName
    String apiVersion
    String appKey
    RequestType requestMethod
    String contentType
    String clientIP
    boolean _isTestRequest = false
}
