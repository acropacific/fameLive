package com.famelive.streamManagement.wowza.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.enums.APIRequestsDetails
import grails.util.GrailsWebUtil
import groovy.util.slurpersupport.GPathResult
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 26/11/14.
 */
class WowzaFetchInStreamInfoResponseCO extends WowzaResponseCO {
    String restURI
    String appInstance
    String serverName = "WowzaStreamingEngine"
    String streamName
    String sourceIP
    boolean isRecordingSet
    boolean isStreamManagerStream
    boolean isPublishedToVOD
    boolean isConnected


    WowzaFetchInStreamInfoResponseCO(InputStream xml, boolean _isTestRequest) {
        if (_isTestRequest) {
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + APIRequestsDetails.FETCH_WOWZA_INCOMING_STREAM_INFO.testResponseFile).getFile()
            xml = (InputStream) new FileInputStream(responseFile)
        }
        GPathResult response = new XmlSlurper().parse(xml)
        restURI = response.attributes().get("restURI")
        Iterator itr = response.childNodes()
        while (itr.hasNext()) {
            try {
                def applicationAdvChildNode = itr.next()
                if (applicationAdvChildNode.name.equals("ApplicationInstance")) {
                    appInstance = applicationAdvChildNode.text()
                } else if (applicationAdvChildNode.name.equals("Name")) {
                    streamName = applicationAdvChildNode.text()
                } else if (applicationAdvChildNode.name.equals("SourceIp")) {
                    sourceIP = applicationAdvChildNode.text()
                } else if (applicationAdvChildNode.name.equals("IsRecordingSet")) {
                    isRecordingSet = Boolean.parseBoolean(applicationAdvChildNode.text())
                } else if (applicationAdvChildNode.name.equals("IsStreamManagerStream")) {
                    isStreamManagerStream = Boolean.parseBoolean(applicationAdvChildNode.text())
                } else if (applicationAdvChildNode.name.equals("IsPublishedToVOD")) {
                    isPublishedToVOD = Boolean.parseBoolean(applicationAdvChildNode.text())
                } else if (applicationAdvChildNode.name.equals("IsConnected")) {
                    isConnected = Boolean.parseBoolean(applicationAdvChildNode.text())
                }
            } catch (IllegalArgumentException exp) {
                println "Exception in WowzaFetchInStreamInfoResponseCO exp:" + exp
            }
        }
    }

    JSONObject toJson() {
        JSONObject response = new JSONObject()
        response.put("success", success)
        response.put("message", message)
        JSONObject data = new JSONObject()
        data.put("ApplicationInstance", appInstance)
        data.put("restURI", restURI)
        data.put("StreamName", streamName)
        data.put("SourceIp", sourceIP)
        data.put("IsRecordingSet", isRecordingSet)
        data.put("IsStreamManagerStream", isStreamManagerStream)
        data.put("IsPublishedToVOD", isPublishedToVOD)
        data.put("IsConnected", isConnected)
        response.put("data", data)
        return response
    }
}

