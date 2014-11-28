package com.famelive.common.streamManagement

class EventInStreamCredentials extends EventStreamCredentials {

    static constraints = {
        validFrom nullable: true
        validTill nullable: true
        event unique: true
    }

    Date validFrom
    Date validTill
}
