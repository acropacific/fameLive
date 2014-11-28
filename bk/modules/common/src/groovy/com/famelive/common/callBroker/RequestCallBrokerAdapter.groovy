package com.famelive.common.callBroker

import com.famelive.common.constant.CommonConstants
import com.famelive.common.enums.CallBrokerType
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created by anil on 17/10/14.
 */
public class RequestCallBrokerAdapter {

    GrailsApplication grailsApplication

    public RequestCallBroker getCallBroker() {
        return grailsApplication.mainContext.getBean(CommonConstants.DEFAULT_REQUEST_CALL_BROKER.value)
    }

    public RequestCallBroker getCallBroker(CallBrokerType callBrokerType) {
        return grailsApplication.mainContext.getBean(callBrokerType?.value)
    }
}
