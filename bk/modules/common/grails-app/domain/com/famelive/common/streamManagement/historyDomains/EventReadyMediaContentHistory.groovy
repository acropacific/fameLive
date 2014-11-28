package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.streamManagement.EventMediaContent

/**
 * Created by anil on 13/11/14.
 */
class EventReadyMediaContentHistory extends EventMediaContent {
    static hasMany = [videoResolutions: Integer]

    static constraints = {
        imagePath nullable: true
        videoPath nullable: true
    }
}
