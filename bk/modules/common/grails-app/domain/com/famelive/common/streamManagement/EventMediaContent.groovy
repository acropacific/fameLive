package com.famelive.common.streamManagement

import com.famelive.common.slotmanagement.Event

class EventMediaContent {

    Event event
    boolean isImageUrl
    String imagePath
    boolean isVideoUrl
    String videoPath
    Set<Integer> videoResolutions = []
    String videoExtension

    static constraints = {
    }
}
