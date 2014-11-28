package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.streamManagement.EventMediaContent

class EventUpcomingMediaContentHistory extends EventMediaContent {
    static hasMany = [videoResolutions: Integer]

    static constraints = {
        imagePath nullable: true
        videoPath nullable: true
    }
}
