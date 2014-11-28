package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.util.AdminDateTimeUtils
import com.famelive.common.command.slotmanagement.EditEventCommand
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

@Validateable
class AdminEditEventCommand extends AdminAuthenticationCommand {
    Long eventId
    String name
    String description
    String startDate
    String startTime
    Long duration
    MultipartFile imageName
    Long genreId
    Long userId

    static constraints = {
        name nullable: true
        description nullable: true
        startTime nullable: true
        startDate nullable: true
        duration nullable: true
        imageName nullable: true
        genreId nullable: true
    }

    @Override
    EditEventCommand toRequestCommand() {
        return new EditEventCommand(id: this?.userId, eventId: this?.eventId, name: this?.name, description: this?.description, startTime: this?.getStartDateTime(), duration: this?.duration, genreIds: [this?.genreId], imageName: this?.imageName?.originalFilename)
    }

    Date getStartDateTime() {
        this?.startDate ? AdminDateTimeUtils?.concatenateDateWithTime(AdminConstants.DATE_TIME_FORMAT, this?.startDate + " " + this?.startTime) : null
    }
}
