package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.FetchGenreCommand
import grails.validation.Validateable

@Validateable
class AdminFetchGenreCommand extends AdminAuthenticationCommand {

    Long genreId

    static constraints = {
        genreId nullable: true
    }

    @Override
    FetchGenreCommand toRequestCommand() {
        new FetchGenreCommand(id: this?.id, genreId: this?.genreId)
    }
}
