package com.famelive.admin.command.slotmanagement

import com.famelive.admin.command.AdminAuthenticationCommand
import com.famelive.common.command.slotmanagement.GenreCommand
import grails.validation.Validateable

@Validateable
class AdminGenreCommand extends AdminAuthenticationCommand {

    String name
    String youtubeLink

    @Override
    GenreCommand toRequestCommand() {
        new GenreCommand(id: this?.id, name: this?.name, youtubeLink: this?.youtubeLink)
    }
}
