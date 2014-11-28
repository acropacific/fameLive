package com.famelive.streamManagement

import com.famelive.api.constant.ApiConstants
import com.famelive.common.callBroker.RequestCallBroker
import com.famelive.common.callBroker.RequestCallBrokerAdapter
import com.famelive.common.callBroker.RequestCallBrokerResponse
import com.famelive.common.enums.CallBrokerType
import com.famelive.common.enums.slotmanagement.EventStatus
import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.slotmanagement.Event
import com.famelive.common.streamManagement.EventInStreamCredentials
import com.famelive.common.streamManagement.EventOutStreamCredentials
import com.famelive.common.streamManagement.EventStreamInfo
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.util.streamManagement.StreamManagementUtil
import com.famelive.streamManagement.api.command.APILoginRequestCO
import com.famelive.streamManagement.api.command.APILoginResponseCO
import com.famelive.streamManagement.command.ChangeEventStateRequestCO
import com.famelive.streamManagement.command.FetchStreamCredentialsRequestCO
import com.famelive.streamManagement.command.FetchStreamCredentialsResponseCO
import com.famelive.streamManagement.command.ResponseCO
import com.famelive.streamManagement.enums.APIRequestsDetails
import com.famelive.streamManagement.util.StreamMessagesUtil
import com.famelive.streamManagement.wowza.AdvanceSetting
import com.famelive.streamManagement.wowza.Publisher
import com.famelive.streamManagement.wowza.command.*
import grails.transaction.Transactional

@Transactional
class StreamingAPIHelperService {
    RequestCallBrokerAdapter requestCallBrokerAdapter
    SchedulerHelperService schedulerHelperService

    ResponseCO login(APILoginRequestCO apiLoginRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker()
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(apiLoginRequestCO)
        ResponseCO apiLoginResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.LOGIN.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                apiLoginResponseCO = new APILoginResponseCO(requestCallBrokerResponse.responseStream, apiLoginRequestCO)
                if (apiLoginResponseCO.status.equals(ApiConstants.MOBILE_API_SUCCESS_CODE)) {
                    apiLoginResponseCO.success = true
                    apiLoginResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.login.success")
                } else {
                    apiLoginResponseCO.success = false
                    apiLoginResponseCO.message = apiLoginResponseCO.message
                }
            } else {
                apiLoginResponseCO.success = false
                apiLoginResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.login.failed.noReadableResponseFromServer")
            }
        } else {
            apiLoginResponseCO.success = false
            apiLoginResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.login.failed.invalidServerResponse")
        }
        return apiLoginResponseCO
    }

    ResponseCO getWowzaApplications(WowzaGetApplicationsRequestCO wowzaGetApplicationsRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaGetApplicationsRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.GET_WOWZA_APPLICATIONS.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO = new WowzaGetApplicationsResponseCO(requestCallBrokerResponse.responseStream, wowzaGetApplicationsRequestCO)
                responseCO.success = true
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.getWowzaApplications.success")
            } else {
                responseCO.success = false
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.getWowzaApplications.failed.noReadableResponseFromServer")

            }
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.getWowzaApplications.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO startWowzaApplication(WowzaStartApplicationRequestCO wowzaStartApplicationRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaStartApplicationRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.START_WOWZA_APPLICATION.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.startWowzaApplication.success")
            } else {
                responseCO.message = responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.startWowzaApplication.failed.noReadableResponseFromServer")
            }
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.startWowzaApplication.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO restartWowzaApplication(WowzaRestartApplicationRequestCO wowzaRestartApplicationRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        ResponseCO responseCO = new ResponseCO()

        /*RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaRestartApplicationRequestCO)
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.RESTART_WOWZA_APPLICATION.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.success")
            } else {
                responseCO.message = responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.failed.noReadableResponseFromServer")
            }
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.failed.invalidServerResponse")
        }*/

        //Wowza Restart application api not working, so we are using stop and then start api to achieve the same
        wowzaRestartApplicationRequestCO.actionName = APIRequestsDetails.STOP_WOWZA_APPLICATION.action
        wowzaRestartApplicationRequestCO.action = "shutdown"
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaRestartApplicationRequestCO)
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.STOP_WOWZA_APPLICATION.successCode) {
            wowzaRestartApplicationRequestCO.actionName = APIRequestsDetails.START_WOWZA_APPLICATION.action
            wowzaRestartApplicationRequestCO.action = "start"
            requestCallBrokerResponse = requestCallBroker.execute(wowzaRestartApplicationRequestCO)
            if (requestCallBrokerResponse.statusCode == APIRequestsDetails.START_WOWZA_APPLICATION.successCode) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.success")
                responseCO.success = true
            } else {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.failed.stoppedButNotStarted")
                responseCO.success = false
            }
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.restartWowzaApplication.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO stopWowzaApplication(WowzaStopApplicationRequestCO wowzaStopApplicationRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaStopApplicationRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.STOP_WOWZA_APPLICATION.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.stopWowzaApplication.success")
            } else {
                responseCO.message = responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.stopWowzaApplication.failed.noReadableResponseFromServer")
            }
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.stopWowzaApplication.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO addWowzaPublisher(WowzaAddPublisherRequestCO wowzaAddPublisherRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaAddPublisherRequestCO)
        ResponseCO wowzaAddPublisherResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.ADD_WOWZA_PUBLISHER.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaAddPublisherResponseCO = new WowzaAddPublisherResponseCO(requestCallBrokerResponse.responseStream, wowzaAddPublisherRequestCO)
                wowzaAddPublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.addWowzaPublisher.success")
            } else {
                wowzaAddPublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.addWowzaPublisher.failed.noReadableResponseFromServer")
            }
            wowzaAddPublisherResponseCO.success = true
        } else {
            wowzaAddPublisherResponseCO.success = false
            wowzaAddPublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.addWowzaPublisher.failed.invalidServerResponse")
        }
        return wowzaAddPublisherResponseCO
    }

    ResponseCO removeWowzaPublisher(WowzaRemovePublisherRequestCO wowzaRemovePublisherRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaRemovePublisherRequestCO)
        ResponseCO wowzaRemovePublisherResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.REMOVE_WOWZA_PUBLISHER.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaRemovePublisherResponseCO = new WowzaRemovePublisherResponseCO(requestCallBrokerResponse.responseStream,)
                wowzaRemovePublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.removeWowzaPublisher.success")
            } else {
                wowzaRemovePublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.removeWowzaPublisher.failed.noReadableResponseFromServer")
            }
            wowzaRemovePublisherResponseCO.success = true
        } else {
            wowzaRemovePublisherResponseCO.success = false
            wowzaRemovePublisherResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.removeWowzaPublisher.failed.invalidServerResponse")
        }
        return wowzaRemovePublisherResponseCO
    }

    void updateWowzaInputStreamFillerVideoDetail(ChangeEventStateRequestCO changeEventStateToReadyRequestCO) {
        Event event = Event.findByEventId(changeEventStateToReadyRequestCO.eventID)
        WowzaChannel channel = EventStreamInfo.findByEvent(event).wowzaChannel
        WowzaUpdateApplicationModuleRequestCO wowzaUpdateApplicationModuleRequestCO = new WowzaUpdateApplicationModuleRequestCO()
        wowzaUpdateApplicationModuleRequestCO.serverIP = channel.serverIP
        wowzaUpdateApplicationModuleRequestCO.applicationName = channel.name
        wowzaUpdateApplicationModuleRequestCO.newValue = changeEventStateToReadyRequestCO.eventID
        wowzaUpdateApplicationModuleRequestCO.actionName = "updateLoopUntilLiveSourceStreamsWowzaApplicationModule"
        System.out.println(updateLoopUntilLiveSourceStreamsWowzaApplicationModule(wowzaUpdateApplicationModuleRequestCO))
    }

    void resetWowzaInputStreamFillerVideoDetail(ChangeEventStateRequestCO changeEventStateToReadyRequestCO) {
        Event event = Event.findByEventId(changeEventStateToReadyRequestCO.eventID)
        WowzaChannel channel = EventStreamInfo.findByEvent(event).wowzaChannel
        WowzaUpdateApplicationModuleRequestCO wowzaUpdateApplicationModuleRequestCO = new WowzaUpdateApplicationModuleRequestCO()
        wowzaUpdateApplicationModuleRequestCO.serverIP = channel.serverIP
        wowzaUpdateApplicationModuleRequestCO.applicationName = channel.name
        wowzaUpdateApplicationModuleRequestCO.newValue = StreamManagementUtil.fetchStreamManagementConstantForChannel(channel, StreamManagementConstantKeys.DEFAULT_STREAM_FOR_FILLER_VIDEO)
        wowzaUpdateApplicationModuleRequestCO.actionName = "updateLoopUntilLiveSourceStreamsWowzaApplicationModule"
        System.out.println(updateLoopUntilLiveSourceStreamsWowzaApplicationModule(wowzaUpdateApplicationModuleRequestCO))
    }

    ResponseCO changeEventStateToReady(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        ResponseCO responseCO = new ResponseCO()
        Event event = Event.findByEventId(changeEventStateRequestCO.eventID)
        if (event != null) {
            isStateChanged = schedulerHelperService.changeEventStateToReady(changeEventStateRequestCO)
        }
        if (isStateChanged) {
            responseCO.success = true
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToReady.success")
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToReady.failed.someErrorOccurred")
        }
        return responseCO
    }

    ResponseCO changeEventStateToPaused(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        ResponseCO responseCO = new ResponseCO()
        Event event = Event.findByEventId(changeEventStateRequestCO.eventID)
        if (event != null) {
            isStateChanged = schedulerHelperService.changeEventStateToPaused(changeEventStateRequestCO)
        }
        if (isStateChanged) {
            responseCO.success = true
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToPaused.success")
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToPaused.failed.someErrorOccurred")
        }
        return responseCO
    }

    ResponseCO changeEventStateToComplete(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        ResponseCO responseCO = new ResponseCO()
        Event event = Event.findByEventId(changeEventStateRequestCO.eventID)
        if (event != null) {
            isStateChanged = schedulerHelperService.changeEventStateToPassed(changeEventStateRequestCO)
        }
        if (isStateChanged) {
            responseCO.success = true
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToComplete.success")
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToComplete.failed.someErrorOccurred")
        }
        return responseCO
    }

    ResponseCO changeEventStateToLive(ChangeEventStateRequestCO changeEventStateRequestCO) {
        boolean isStateChanged
        ResponseCO responseCO = new ResponseCO()
        Event event = Event.findByEventId(changeEventStateRequestCO.eventID)
        if (event != null) {
            isStateChanged = schedulerHelperService.changeEventStateToLive(changeEventStateRequestCO)
        }
        if (isStateChanged) {
            responseCO.success = true
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToLive.success")
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.changeEventStateToLive.failed.someErrorOccurred")
        }
        return responseCO
    }

    ResponseCO fetchInStreamCredentials(FetchStreamCredentialsRequestCO fetchStreamCredentialsRequestCO) {
        Event event = Event.findByEventId(fetchStreamCredentialsRequestCO.eventID)
        Publisher inStreamPublisher
        FetchStreamCredentialsResponseCO fetchStreamCredentialsResponseCO = new FetchStreamCredentialsResponseCO()

        EventInStreamCredentials eventInStreamCredentials = EventInStreamCredentials.findByEvent(event)
        if (event == null) {
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchInStreamCredentials.failed.invalidEventID")
        } else if (!isEventStreamCredentialsCanBeProvided(event)) {
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchInStreamCredentials.failed.credentialsCanNotBeProvided")
        } else if (eventInStreamCredentials) {
            inStreamPublisher = new Publisher(username: eventInStreamCredentials.username, password: eventInStreamCredentials.password)
        } else {
            EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(event)
            WowzaChannel wowzaChannel = eventStreamInfo.wowzaChannel
            inStreamPublisher = schedulerHelperService.addPublisher(wowzaChannel)
            if (inStreamPublisher) {
                eventInStreamCredentials = new EventInStreamCredentials(username: inStreamPublisher.username, password: inStreamPublisher.password, event: event, validFrom: event.startTime, validTill: event.endTime)
                eventInStreamCredentials.save(failOnError: true, flush: true)
            }
        }
        if (inStreamPublisher) {
            fetchStreamCredentialsResponseCO.success = true
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchInStreamCredentials.success")
            fetchStreamCredentialsResponseCO.publisher = inStreamPublisher
        } else {
            fetchStreamCredentialsResponseCO.success = false
            if (fetchStreamCredentialsResponseCO.message == null) {
                fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchInStreamCredentials.failed.credentialsCouldNotBeGenerated")
            }
        }

        return fetchStreamCredentialsResponseCO
    }

    ResponseCO fetchOutStreamCredentials(FetchStreamCredentialsRequestCO fetchStreamCredentialsRequestCO) {
        Event event = Event.findByEventId(fetchStreamCredentialsRequestCO.eventID)
        Publisher outStreamPublisher
        FetchStreamCredentialsResponseCO fetchStreamCredentialsResponseCO = new FetchStreamCredentialsResponseCO()

        EventOutStreamCredentials eventOutStreamCredentials = EventOutStreamCredentials.findByEvent(event)
        if (event == null) {
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchOutStreamCredentials.failed.invalidEventID")
        } else if (!isEventStreamCredentialsCanBeProvided(event)) {
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchOutStreamCredentials.failed.credentialsCanNotBeProvided")
        } else if (eventOutStreamCredentials) {
            outStreamPublisher = new Publisher(username: eventOutStreamCredentials.username, password: eventOutStreamCredentials.password)
        } else {
            EventStreamInfo eventStreamInfo = EventStreamInfo.findByEvent(event)
            WowzaChannel wowzaChannel = eventStreamInfo.wowzaChannel
            outStreamPublisher = schedulerHelperService.addPublisher(wowzaChannel)
            if (outStreamPublisher) {
                eventOutStreamCredentials = new EventOutStreamCredentials(username: outStreamPublisher.username, password: outStreamPublisher.password, event: event, validFrom: event.startTime, validTill: event.endTime)
                eventOutStreamCredentials.save(failOnError: true, flush: true)
            }
        }
        if (outStreamPublisher) {
            fetchStreamCredentialsResponseCO.success = true
            fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchOutStreamCredentials.success")
            fetchStreamCredentialsResponseCO.publisher = outStreamPublisher
        } else {
            fetchStreamCredentialsResponseCO.success = false
            if (fetchStreamCredentialsResponseCO.message == null) {
                fetchStreamCredentialsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchOutStreamCredentials.failed.credentialsCouldNotBeGenerated")
            }
        }
        return fetchStreamCredentialsResponseCO
    }

    boolean isEventStreamCredentialsCanBeProvided(Event event) {
        boolean status
        if (event.status == EventStatus.READY || event.status == EventStatus.ON_AIR || event.status == EventStatus.PAUSED) {
            status = true
        }
        return status
    }

    ResponseCO fetchAdvanceWowzaApplicationConfiguration(WowzaFetchAdvanceApplicationConfigurationRequestCO wowzaFetchAdvanceApplicationConfigurationRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaFetchAdvanceApplicationConfigurationRequestCO)
        ResponseCO wowzaFetchAdvanceApplicationConfigurationResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.FETCH_ADVANCE_WOWZA_APPLICATION_CONFIGURATION.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaFetchAdvanceApplicationConfigurationResponseCO = new WowzaFetchAdvanceApplicationConfigurationResponseCO(requestCallBrokerResponse.responseStream, wowzaFetchAdvanceApplicationConfigurationRequestCO._isTestRequest)
                wowzaFetchAdvanceApplicationConfigurationResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchAdvanceWowzaApplicationConfiguration.success")
            } else {
                wowzaFetchAdvanceApplicationConfigurationResponseCO.message = wowzaFetchAdvanceApplicationConfigurationResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchAdvanceWowzaApplicationConfiguration.failed.noReadableResponseFromServer")
            }
            wowzaFetchAdvanceApplicationConfigurationResponseCO.success = true
        } else {
            wowzaFetchAdvanceApplicationConfigurationResponseCO.success = false
            wowzaFetchAdvanceApplicationConfigurationResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchAdvanceWowzaApplicationConfiguration.failed.invalidServerResponse")
        }
        return wowzaFetchAdvanceApplicationConfigurationResponseCO
    }

    ResponseCO updateLoopUntilLiveSourceStreamsWowzaApplicationModule(WowzaUpdateApplicationModuleRequestCO wowzaUpdateApplicationModuleRequestCO) {
        WowzaFetchAdvanceApplicationConfigurationRequestCO wowzaFetchAdvanceApplicationConfigurationRequestCO = new WowzaFetchAdvanceApplicationConfigurationRequestCO()
        wowzaFetchAdvanceApplicationConfigurationRequestCO.setActionName("fetchAdvanceWowzaApplicationConfiguration")
        wowzaFetchAdvanceApplicationConfigurationRequestCO.setApplicationName(wowzaUpdateApplicationModuleRequestCO.applicationName)
        WowzaFetchAdvanceApplicationConfigurationResponseCO wowzaFetchAdvanceApplicationConfigurationResponseCO = fetchAdvanceWowzaApplicationConfiguration(wowzaFetchAdvanceApplicationConfigurationRequestCO)
        AdvanceSetting loopUntilLiveSourceStreamsSetting = wowzaFetchAdvanceApplicationConfigurationResponseCO.advanceSettings.find { advanceSetting ->
            if (advanceSetting.name.equals("loopUntilLiveSourceStreams")) {
                return true
            }
        }
        wowzaUpdateApplicationModuleRequestCO.advanceSetting = loopUntilLiveSourceStreamsSetting
        wowzaUpdateApplicationModuleRequestCO.advanceSetting.value = wowzaUpdateApplicationModuleRequestCO.newValue
        wowzaUpdateApplicationModuleRequestCO.modules = wowzaFetchAdvanceApplicationConfigurationResponseCO.modules
        wowzaUpdateApplicationModuleRequestCO.restURI = wowzaFetchAdvanceApplicationConfigurationResponseCO.restURI
        wowzaUpdateApplicationModuleRequestCO.version = wowzaFetchAdvanceApplicationConfigurationResponseCO.version
        wowzaUpdateApplicationModuleRequestCO.serverName = wowzaFetchAdvanceApplicationConfigurationResponseCO.serverName
        wowzaUpdateApplicationModuleRequestCO.saveFieldList = wowzaFetchAdvanceApplicationConfigurationResponseCO.saveFieldList

        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaUpdateApplicationModuleRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.UPDATE_LOOP_UNTIL_LIVE_SOURCE_STREAMS_WOWZA_APPLICATION_MODULE.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.updateLoopUntilLiveSourceStreamsWowzaApplicationModule.success")
            } else {
                responseCO.message = wowzaFetchAdvanceApplicationConfigurationResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.updateLoopUntilLiveSourceStreamsWowzaApplicationModule.failed.noReadableResponseFromServer")
            }
            //Going to restart wowza application
            WowzaRestartApplicationRequestCO wowzaRestartApplicationRequestCO = new WowzaRestartApplicationRequestCO(actionName: "startWowzaApplication", applicationName: wowzaFetchAdvanceApplicationConfigurationRequestCO.applicationName)
            responseCO.message += " & " + restartWowzaApplication(wowzaRestartApplicationRequestCO)
            System.out.println(responseCO.message)
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.updateLoopUntilLiveSourceStreamsWowzaApplicationModule.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO fetchWowzaIncomingStreamInfo(WowzaFetchInStreamInfoRequestCO wowzaFetchInStreamInfoRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaFetchInStreamInfoRequestCO)
        ResponseCO wowzaFetchInStreamInfoResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.FETCH_ADVANCE_WOWZA_APPLICATION_CONFIGURATION.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaFetchInStreamInfoResponseCO = new WowzaFetchInStreamInfoResponseCO(requestCallBrokerResponse.responseStream, wowzaFetchInStreamInfoRequestCO._isTestRequest)
                wowzaFetchInStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamInfo.success")
            } else {
                wowzaFetchInStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamInfo.failed.noReadableResponseFromServer")
            }
            wowzaFetchInStreamInfoResponseCO.success = true
        } else {
            wowzaFetchInStreamInfoResponseCO.success = false
            wowzaFetchInStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamInfo.failed.invalidServerResponse")
        }
        return wowzaFetchInStreamInfoResponseCO
    }

    ResponseCO fetchWowzaOutgoingStreamInfo(WowzaFetchOutStreamInfoRequestCO wowzaFetchOutStreamInfoRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaFetchOutStreamInfoRequestCO)
        ResponseCO wowzaFetchOutStreamInfoResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.FETCH_WOWZA_OUTGOING_STREAM_INFO.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaFetchOutStreamInfoResponseCO = new WowzaFetchOutStreamInfoResponseCO(requestCallBrokerResponse.responseStream, wowzaFetchOutStreamInfoRequestCO._isTestRequest)
                wowzaFetchOutStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaOutgoingStreamInfo.success")
            } else {
                wowzaFetchOutStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaOutgoingStreamInfo.failed.noReadableResponseFromServer")
            }
            wowzaFetchOutStreamInfoResponseCO.success = true
        } else {
            wowzaFetchOutStreamInfoResponseCO.success = false
            wowzaFetchOutStreamInfoResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaOutgoingStreamInfo.failed.invalidServerResponse")
        }
        return wowzaFetchOutStreamInfoResponseCO
    }

    ResponseCO fetchWowzaIncomingStreamStatistics(WowzaFetchInStreamStatisticsRequestCO wowzaFetchInStreamStatisticsRequestCO) {
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaFetchInStreamStatisticsRequestCO)
        ResponseCO wowzaFetchInStreamStatisticsResponseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.FETCH_WOWZA_INCOMING_STREAM_STATISTICS.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                wowzaFetchInStreamStatisticsResponseCO = new WowzaFetchInStreamStatisticsResponseCO(requestCallBrokerResponse.responseStream, wowzaFetchInStreamStatisticsRequestCO._isTestRequest)
                wowzaFetchInStreamStatisticsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamStatistics.success")
            } else {
                wowzaFetchInStreamStatisticsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamStatistics.failed.noReadableResponseFromServer")
            }
            wowzaFetchInStreamStatisticsResponseCO.success = true
        } else {
            wowzaFetchInStreamStatisticsResponseCO.success = false
            wowzaFetchInStreamStatisticsResponseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.fetchWowzaIncomingStreamStatistics.failed.invalidServerResponse")
        }
        return wowzaFetchInStreamStatisticsResponseCO
    }

    ResponseCO resetWowzaIncomingStream(WowzaDoActionOnInStreamRequestCO wowzaDoActionOnInStreamRequestCO) {
        wowzaDoActionOnInStreamRequestCO.setInStreamAction("resetStream")
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaDoActionOnInStreamRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.RESET_WOWZA_INCOMING_STREAM.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.resetWowzaIncomingStream.success")
            } else {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.resetWowzaIncomingStream.failed.noReadableResponseFromServer")
            }
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.resetWowzaIncomingStream.failed.invalidServerResponse")
        }
        return responseCO
    }

    ResponseCO disconnectWowzaIncomingStream(WowzaDoActionOnInStreamRequestCO wowzaDoActionOnInStreamRequestCO) {
        wowzaDoActionOnInStreamRequestCO.setInStreamAction("disconnectStream")
        RequestCallBroker requestCallBroker = requestCallBrokerAdapter.getCallBroker(CallBrokerType.WOWZA_CALL_BROKER)
        RequestCallBrokerResponse requestCallBrokerResponse = requestCallBroker.execute(wowzaDoActionOnInStreamRequestCO)
        ResponseCO responseCO = new ResponseCO()
        if (requestCallBrokerResponse.statusCode == APIRequestsDetails.DISCONNECT_WOWZA_INCOMING_STREAM.successCode) {
            if (requestCallBrokerResponse.responseStream) {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.disconnectWowzaIncomingStream.success")
            } else {
                responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.disconnectWowzaIncomingStream.failed.noReadableResponseFromServer")
            }
            responseCO.success = true
        } else {
            responseCO.success = false
            responseCO.message = StreamMessagesUtil.messageSource.getProperty("stream.disconnectWowzaIncomingStream.failed.invalidServerResponse")
        }
        return responseCO
    }
}
