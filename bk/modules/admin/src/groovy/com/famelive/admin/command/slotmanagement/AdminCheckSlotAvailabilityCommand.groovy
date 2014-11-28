package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.util.AdminDateTimeUtils
import com.famelive.common.command.slotmanagement.CheckSlotAvailabilityCommand
import grails.validation.Validateable

@Validateable
class AdminCheckSlotAvailabilityCommand extends AdminAuthenticationCommand {

    String startDate
    String startTime
    Long duration

    static constraints = {
        startTime nullable: true
        startDate nullable: true
        duration nullable: true
    }

    @Override
    CheckSlotAvailabilityCommand toRequestCommand() {
        new CheckSlotAvailabilityCommand(id: this?.id, startTime: this?.getStartDateTime(), duration: this?.duration)
    }

    Date getStartDateTime() {
        this?.startDate ? AdminDateTimeUtils?.concatenateDateWithTime(AdminConstants.DATE_TIME_FORMAT, this?.startDate + " " + this?.startTime) : null
    }
}
