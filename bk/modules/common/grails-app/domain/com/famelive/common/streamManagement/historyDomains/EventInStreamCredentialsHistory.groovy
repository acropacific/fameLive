package com.famelive.common.streamManagement.historyDomains

import com.famelive.common.streamManagement.EventStreamCredentials

class EventInStreamCredentialsHistory extends EventStreamCredentials {

    static constraints = {
        validFrom nullable: true
        validTill nullable: true
        event unique: true
    }

    Date validFrom
    Date validTill
}
