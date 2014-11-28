package com.famelive.streamManagement

import com.famelive.common.callBroker.RequestCallBrokerAdapter
import com.famelive.streamManagement.api.command.APILoginRequestCO
import com.famelive.streamManagement.command.ChangeEventStateRequestCO
import com.famelive.streamManagement.command.FetchStreamCredentialsRequestCO
import com.famelive.streamManagement.command.ResponseCO
import com.famelive.streamManagement.wowza.command.*
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.web.json.JSONObject

//@Transactional
class APIV1Service implements APIService {
    RequestCallBrokerAdapter requestCallBrokerAdapter
    SchedulerHelperService schedulerHelperService
    StreamingAPIHelperService streamingAPIHelperService

    ResponseCO login(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        APILoginRequestCO apiLoginRequestCO = new APILoginRequestCO(jsonSlurper)
        streamingAPIHelperService.login(apiLoginRequestCO)
    }

    ResponseCO getWowzaApplications(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaGetApplicationsRequestCO wowzaGetApplicationsRequestCO = new WowzaGetApplicationsRequestCO(jsonSlurper)
        streamingAPIHelperService.getWowzaApplications(wowzaGetApplicationsRequestCO)
    }

    ResponseCO addWowzaPublisher(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaAddPublisherRequestCO wowzaAddPublisherRequestCO = new WowzaAddPublisherRequestCO(jsonSlurper)
        streamingAPIHelperService.addWowzaPublisher(wowzaAddPublisherRequestCO)
    }

    ResponseCO removeWowzaPublisher(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaRemovePublisherRequestCO wowzaRemovePublisherRequestCO = new WowzaRemovePublisherRequestCO(jsonSlurper)
        streamingAPIHelperService.removeWowzaPublisher(wowzaRemovePublisherRequestCO)
    }

    ResponseCO changeEventStateToReady(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        ChangeEventStateRequestCO changeEventStateRequestCO = new ChangeEventStateRequestCO(jsonSlurper)
        streamingAPIHelperService.changeEventStateToReady(changeEventStateRequestCO)
    }

    ResponseCO changeEventStateToPaused(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        ChangeEventStateRequestCO changeEventStateRequestCO = new ChangeEventStateRequestCO(jsonSlurper)
        streamingAPIHelperService.changeEventStateToPaused(changeEventStateRequestCO)
    }

    ResponseCO changeEventStateToComplete(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        ChangeEventStateRequestCO changeEventStateRequestCO = new ChangeEventStateRequestCO(jsonSlurper)
        streamingAPIHelperService.changeEventStateToComplete(changeEventStateRequestCO)
    }

    ResponseCO changeEventStateToLive(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        ChangeEventStateRequestCO changeEventStateRequestCO = new ChangeEventStateRequestCO(jsonSlurper)
        streamingAPIHelperService.changeEventStateToLive(changeEventStateRequestCO)
    }

    ResponseCO fetchInStreamCredentials(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        FetchStreamCredentialsRequestCO fetchStreamCredentialsRequestCO = new FetchStreamCredentialsRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchInStreamCredentials(fetchStreamCredentialsRequestCO)
    }

    ResponseCO fetchOutStreamCredentials(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        FetchStreamCredentialsRequestCO fetchStreamCredentialsRequestCO = new FetchStreamCredentialsRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchOutStreamCredentials(fetchStreamCredentialsRequestCO)
    }

    ResponseCO startWowzaApplication(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaStartApplicationRequestCO wowzaStartApplicationRequestCO = new WowzaStartApplicationRequestCO(jsonSlurper)
        streamingAPIHelperService.startWowzaApplication(wowzaStartApplicationRequestCO)
    }

    ResponseCO stopWowzaApplication(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaStopApplicationRequestCO wowzaStopApplicationRequestCO = new WowzaStopApplicationRequestCO(jsonSlurper)
        streamingAPIHelperService.stopWowzaApplication(wowzaStopApplicationRequestCO)
    }

    ResponseCO restartWowzaApplication(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaRestartApplicationRequestCO wowzaRestartApplicationRequestCO = new WowzaRestartApplicationRequestCO(jsonSlurper)
        streamingAPIHelperService.restartWowzaApplication(wowzaRestartApplicationRequestCO)
    }

    ResponseCO fetchAdvanceWowzaApplicationConfiguration(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaFetchAdvanceApplicationConfigurationRequestCO wowzaFetchAdvanceApplicationConfigurationRequestCO = new WowzaFetchAdvanceApplicationConfigurationRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchAdvanceWowzaApplicationConfiguration(wowzaFetchAdvanceApplicationConfigurationRequestCO)
    }

    ResponseCO updateLoopUntilLiveSourceStreamsWowzaApplicationModule(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaUpdateApplicationModuleRequestCO wowzaUpdateApplicationModuleRequestCO = new WowzaUpdateApplicationModuleRequestCO(jsonSlurper)
        streamingAPIHelperService.updateLoopUntilLiveSourceStreamsWowzaApplicationModule(wowzaUpdateApplicationModuleRequestCO)
    }

    ResponseCO fetchWowzaIncomingStreamInfo(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaFetchInStreamInfoRequestCO wowzaFetchInStreamInfoRequestCO = new WowzaFetchInStreamInfoRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchWowzaIncomingStreamInfo(wowzaFetchInStreamInfoRequestCO)
    }

    ResponseCO fetchWowzaOutgoingStreamInfo(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaFetchOutStreamInfoRequestCO wowzaFetchOutStreamInfoRequestCO = new WowzaFetchOutStreamInfoRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchWowzaOutgoingStreamInfo(wowzaFetchOutStreamInfoRequestCO)
    }

    ResponseCO fetchWowzaIncomingStreamStatistics(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaFetchInStreamStatisticsRequestCO wowzaFetchInStreamStatisticsRequestCO = new WowzaFetchInStreamStatisticsRequestCO(jsonSlurper)
        streamingAPIHelperService.fetchWowzaIncomingStreamStatistics(wowzaFetchInStreamStatisticsRequestCO)
    }

    ResponseCO resetWowzaIncomingStream(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaDoActionOnInStreamRequestCO wowzaDoActionOnInStreamRequestCO = new WowzaDoActionOnInStreamRequestCO(jsonSlurper)
        streamingAPIHelperService.resetWowzaIncomingStream(wowzaDoActionOnInStreamRequestCO)
    }

    ResponseCO disconnectWowzaIncomingStream(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        WowzaDoActionOnInStreamRequestCO wowzaDoActionOnInStreamRequestCO = new WowzaDoActionOnInStreamRequestCO(jsonSlurper)
        streamingAPIHelperService.disconnectWowzaIncomingStream(wowzaDoActionOnInStreamRequestCO)
    }

    ResponseCO recordWowzaLiveStream(JSONObject json) {
        def jsonSlurper = new JsonSlurper().parseText(json.toString())
        //TODO: implement functinality
        return new ResponseCO()
    }
}
