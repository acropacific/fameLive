package com.famelive.streamManagement.enums

/**
 * Created by anil on 18/11/14.
 */
enum APIRequestsDetails {
    LOGIN(200, "login", "loginResponse.json"),
    GET_WOWZA_APPLICATIONS(200, "getWowzaApplications", "getWowzaApplicationsResponse.xml"),
    ADD_WOWZA_PUBLISHER(201, "addWowzaPublisher", "addWowzaPublisherResponse.xml"),
    REMOVE_WOWZA_PUBLISHER(200, "removeWowzaPublisher", "removeWowzaPublisherResponse.xml"),
    CHANGE_EVENT_STATE_TO_READY(0, "changeEventStateToReady", "changeEventStateToReadyResponse.xml"),
    CHANGE_EVENT_STATE_TO_PAUSED(0, "changeEventStateToPaused", "changeEventStateToPausedResponse.xml"),
    CHANGE_EVENT_STATE_TO_COMPLETE(0, "changeEventStateToComplete", "changeEventStateToCompleteResponse.xml"),
    CHANGE_EVENT_STATE_TO_LIVE(0, "changeEventStateToLive", "changeEventStateToLiveResponse.xml"),
    FETCH_IN_STREAM_CREDENTIALS(0, "fetchInStreamCredentials", "fetchInStreamCredentialsResponse.xml"),
    FETCH_OUT_STREAM_CREDENTIALS(0, "fetchOutStreamCredentials", "fetchOutStreamCredentialsResponse.xml"),
    START_WOWZA_APPLICATION(200, "startWowzaApplication", "startWowzaApplicationResponse.xml"),
    STOP_WOWZA_APPLICATION(200, "stopWowzaApplication", "stopWowzaApplicationResponse.xml"),
    RESTART_WOWZA_APPLICATION(200, "restartWowzaApplication", "restartWowzaApplicationResponse.xml"),
    FETCH_ADVANCE_WOWZA_APPLICATION_CONFIGURATION(200, "fetchAdvanceWowzaApplicationConfiguration", "fetchAdvanceWowzaApplicationConfigurationResponse.xml"),
    UPDATE_LOOP_UNTIL_LIVE_SOURCE_STREAMS_WOWZA_APPLICATION_MODULE(200, "updateLoopUntilLiveSourceStreamsWowzaApplicationModule", "updateLoopUntilLiveSourceStreamsWowzaApplicationModuleResponse.xml"),
    FETCH_WOWZA_INCOMING_STREAM_INFO(200, "fetchWowzaIncomingStreamInfo", "fetchWowzaIncomingStreamInfoResponse.xml"),
    FETCH_WOWZA_OUTGOING_STREAM_INFO(200, "fetchWowzaOutgoingStreamInfo", "fetchWowzaOutgoingStreamInfoResponse.xml"),
    FETCH_WOWZA_INCOMING_STREAM_STATISTICS(200, "fetchWowzaIncomingStreamStatistics", "fetchWowzaIncomingStreamStatisticsResponse.xml"),
    RESET_WOWZA_INCOMING_STREAM(200, "resetWowzaIncomingStream", "resetWowzaIncomingStreamResponse.xml"),
    DISCONNECT_WOWZA_INCOMING_STREAM(200, "disconnectWowzaIncomingStream", "disconnectWowzaIncomingStreamResponse.xml")

    int successCode
    String action
    String testResponseFile

    APIRequestsDetails(int successCode, String action, String testResponseFile) {
        this.successCode = successCode
        this.action = action
        this.testResponseFile = testResponseFile
    }

    static final private Map<String, APIRequestsDetails> ACTION_MAP = new HashMap<String, APIRequestsDetails>();

    static {
        for (APIRequestsDetails request : APIRequestsDetails.values()) {
            // ignoring the case by normalizing to uppercase
            ACTION_MAP.put(request.action, request);
        }
    }

    static public APIRequestsDetails find(String action) {
        if (action == null) throw new NullPointerException("Provided action is null");
        APIRequestsDetails request = ACTION_MAP.get(action);
        if (request == null) throw new IllegalArgumentException("Not a valid action: " + action);
        return request;
    }
}
