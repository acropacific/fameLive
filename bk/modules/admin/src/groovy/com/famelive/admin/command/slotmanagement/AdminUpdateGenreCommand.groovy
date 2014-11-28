package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.UpdateGenreCommand
import grails.validation.Validateable

@Validateable
class AdminUpdateGenreCommand extends AdminAuthenticationCommand {

    Long genreId
    String name
    String youtubeLink

    @Override
    UpdateGenreCommand toRequestCommand() {
        new UpdateGenreCommand(id: this?.id, genreId: this?.genreId, name: this?.name, youtubeLink: this?.youtubeLink)
    }
}
