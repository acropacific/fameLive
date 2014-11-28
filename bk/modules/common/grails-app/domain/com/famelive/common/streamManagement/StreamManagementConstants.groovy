package com.famelive.common.streamManagement


import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys

class StreamManagementConstants {
    StreamManagementConstantKeys constant
    String userRole
    WowzaChannel channel
    String value

    static constraints = {
        constant nullable: false, unique: ['userRole', 'channel']
        userRole nullable: true
        channel nullable: true
        value nullable: false
    }
}
