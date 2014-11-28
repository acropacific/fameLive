package com.famelive.streamManagement.wowza.command

import com.famelive.common.constant.CommonConstants
import com.famelive.streamManagement.enums.APIRequestsDetails
import grails.util.GrailsWebUtil
import groovy.util.slurpersupport.GPathResult
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created by anil on 26/11/14.
 */
class WowzaFetchInStreamStatisticsResponseCO extends WowzaResponseCO {
    String restURI
    String version
    String serverName = "WowzaStreamingEngine"
    String applicationInstance
    String streamName
    long uptime
    long bytesIn
    long bytesOut
    long bytesInRate
    long bytesOutRate
    long totalConnections
    Map<String, Integer> outConnections = [:]


    WowzaFetchInStreamStatisticsResponseCO(InputStream xml, boolean _isTestRequest) {
        if (_isTestRequest) {
            File responseFile = GrailsWebUtil.currentApplication().mainContext.getResource(CommonConstants.WOWZA_API_TEST_RESPONSE_PATH + "/" + APIRequestsDetails.FETCH_WOWZA_INCOMING_STREAM_STATISTICS.testResponseFile).getFile()
            xml = (InputStream) new FileInputStream(responseFile)
        }
        GPathResult response = new XmlSlurper().parse(xml)
        restURI = response.attributes().get("restURI")
        Iterator itr = response.childNodes()
        while (itr.hasNext()) {
            def applicationAdvChildNode = itr.next()
            if (applicationAdvChildNode.name.equals("ApplicationInstance")) {
                applicationInstance = applicationAdvChildNode.text()
            } else if (applicationAdvChildNode.name.equals("Name")) {
                streamName = applicationAdvChildNode.text()
            } else if (applicationAdvChildNode.name.equals("Uptime")) {
                uptime = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("BytesIn")) {
                bytesIn = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("BytesOut")) {
                bytesOut = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("BytesInRate")) {
                bytesInRate = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("BytesOutRate")) {
                bytesOutRate = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("TotalConnections")) {
                totalConnections = Long.parseLong(applicationAdvChildNode.text())
            } else if (applicationAdvChildNode.name.equals("ConnectionCount")) {
                Iterator connectionsNodeItr = applicationAdvChildNode.childNodes()
                while (connectionsNodeItr.hasNext()) {
                    def connectionNode = connectionsNodeItr.next()
                    if (connectionNode.name.equals("entry")) {
                        Iterator entryNodeItr = connectionNode.childNodes()
                        String connectionType
                        int connectionCount
                        while (entryNodeItr.hasNext()) {
                            try {
                                def entryNode = entryNodeItr.next()
                                if (entryNode.name.equals("string")) {
                                    connectionType = entryNode.text()
                                } else if (entryNode.name.equals("int")) {
                                    connectionCount = Integer.parseInt(entryNode.text())
                                }
                            } catch (IllegalArgumentException exp) {
                                println "Exception in WowzaFetchInStreamStatisticsResponseCO, exp:" + exp
                            }
                        }
                        outConnections.put(connectionType, connectionCount)
                    }
                }
            }
        }
    }

    JSONObject toJson() {
        JSONObject response = new JSONObject()
        response.put("success", success)
        response.put("message", message)
        JSONObject data = new JSONObject()
        data.put("ApplicationInstance", applicationInstance)
        data.put("restURI", restURI)
        data.put("StreamName", streamName)
        data.put("Uptime", uptime)
        data.put("BytesIn", bytesIn)
        data.put("BytesOut", bytesOut)
        data.put("BytesInRate", bytesInRate)
        data.put("BytesOutRate", bytesOutRate)
        data.put("TotalConnections", totalConnections)
        data.put("OutConnections", outConnections as JSONObject)
        response.put("data", data)
        return response
    }
}

