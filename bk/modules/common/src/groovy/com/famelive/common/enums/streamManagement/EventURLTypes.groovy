package com.famelive.common.enums.streamManagement

/**
 * Created by anil on 16/11/14.
 */
public enum EventURLTypes {
    EVENT_OUT_STREAM_URL("eventOutStreamUrl"),
    EVENT_UPCOMING_STREAM_URL("eventUpcomingStreamUrl"),
    EVENT_READY_STREAM_URL("eventReadyStreamUrl"),
    EVENT_COMPLETE_STREAM_URL("EventCompleteStreamUrl")
    String value

    EventURLTypes(String value) {
        this.value = value
    }

}