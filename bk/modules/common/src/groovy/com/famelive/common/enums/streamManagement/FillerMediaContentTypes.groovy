package com.famelive.common.enums.streamManagement

/**
 * Created by anil on 25/11/14.
 */
enum FillerMediaContentTypes {
    EVENT_READY_FILLER_VIDEO("eventReadyFillerVideo"),
    EVENT_UPCOMING_FILLER_VIDEO("eventUpcomingFillerVideo"),
    EVENT_OFFLINE_FILLER_VIDEO("eventOfflineFillerVideo"),
    EVENT_COMPLETE_FILLER_VIDEO("EventCompleteFillerVideo"),
    EVENT_READY_FILLER_IMAGE("eventReadyFillerImage"),
    EVENT_UPCOMING_FILLER_IMAGE("eventUpcomingFillerImage"),
    EVENT_OFFLINE_FILLER_IMAGE("eventOfflineFillerImage"),
    EVENT_COMPLETE_FILLER_IMAGE("EventCompleteFillerImage")
    String value

    FillerMediaContentTypes(String value) {
        this.value = value
    }
}
