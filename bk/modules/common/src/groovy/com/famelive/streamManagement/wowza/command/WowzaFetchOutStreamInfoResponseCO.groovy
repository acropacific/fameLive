package com.famelive.streamManagement.wowza.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.enums.APIRequestsDetails
import grails.util.GrailsWebUtil
import groovy.util.slurpersupport.GPathResult
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 26/11/14.
 */
class WowzaFetchOutStreamInfoResponseCO extends WowzaResponseCO {
    String restURI
    String serverName = "WowzaStreamingEngine"
    String streamName
    Map<String, String> streamProperties = [:]


    WowzaFetchOutStreamInfoResponseCO(InputStream xml, boolean _isTestRequest) {
        if (_isTestRequest) {
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + APIRequestsDetails.FETCH_WOWZA_OUTGOING_STREAM_INFO.testResponseFile).getFile()
            xml = (InputStream) new FileInputStream(responseFile)
        }
        GPathResult response = new XmlSlurper().parse(xml)
        restURI = response.attributes().get("restURI")
        Iterator itr = response.childNodes()
        while (itr.hasNext()) {
            try {
                def propertiesNode = itr.next()
                if (propertiesNode.name.equals("Name")) {
                    streamName = propertiesNode.text()
                } else {
                    streamProperties.put(propertiesNode.name, propertiesNode.text())
                }
            } catch (IllegalArgumentException exp) {
                println "Exception in WowzaFetchOutStreamInfoResponseCO exp:" + exp
            }
        }
    }

    JSONObject toJson() {
        JSONObject response = new JSONObject()
        response.put("success", success)
        response.put("message", message)
        JSONObject data = new JSONObject()
        data.put("restURI", restURI)
        data.put("StreamName", streamName)
        data.put("StreamProperties", streamProperties as JSONObject)
        response.put("data", data)
        return response
    }
}

