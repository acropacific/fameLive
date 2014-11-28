package com.famelive.common.slotmanagement

import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.streamManagement.WowzaChannel

class SlotManagementConstants {
    SlotManagementConstantKeys constant
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
