package com.famelive.streamManagement.wowza.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.command.RequestCO
import com.famelive.streamManagement.enums.APIRequestsDetails
import com.famelive.streamManagement.wowza.Application
import grails.util.GrailsWebUtil
import groovy.util.slurpersupport.GPathResult

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Created by anil on 27/10/14.
 */

@XmlRootElement(name = "Applications")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorType(XmlAccessType.NONE)
class WowzaGetApplicationsResponseCO extends WowzaResponseCO {

    @XmlElement
    List<Application> applications = []

    WowzaGetApplicationsResponseCO(InputStream xml, RequestCO requestCO) {
        if (requestCO._isTestRequest) {
            APIRequestsDetails requestActionDetails = APIRequestsDetails.GET_WOWZA_APPLICATIONS
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + requestActionDetails.testResponseFile).getFile()
            xml = (InputStream) new FileInputStream(responseFile)
        }
        GPathResult response = new XmlSlurper().parse(xml)
//        this.restURI=response.attributes.restURI
        Iterator itr = response.childNodes()
//        List applications=response.getProperty("Application")
        while (itr.hasNext()) {
            def app = itr.next()
            Application application = new Application()
            application.id = app.attributes.id
            application.href = app.attributes.href
            for (def child : app.children) {
                boolean propValue = false;
                try {
                    propValue = Boolean.parseBoolean(child.text())
                } catch (Exception e) {
                }
                if (child.name == "AppType") {
                    application.appType = child.text()
                } else if (child.name == "DVREnabled") {
                    application.dvrEnabled = propValue
                } else if (child.name == "DRMEnabled") {
                    application.drmEnabled = propValue
                } else if (child.name == "TranscoderEnabled") {
                    application.transcoderEnabled = propValue
                }
            }
            this.applications.add(application)
        }
    }
}
