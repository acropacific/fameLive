package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.ChangeGenreStatusCommand
import com.famelive.common.enums.slotmanagement.GenreStatus
import grails.validation.Validateable

@Validateable
class AdminChangeGenreStatusCommand extends AdminAuthenticationCommand {

    Long genreId
    GenreStatus genreStatus

    @Override
    ChangeGenreStatusCommand toRequestCommand() {
        new ChangeGenreStatusCommand(id: this?.id, genreId: this.genreId, genreStatus: this.genreStatus)
    }
}
