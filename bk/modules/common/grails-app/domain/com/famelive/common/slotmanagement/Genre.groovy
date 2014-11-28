package com.famelive.common.slotmanagement

import com.famelive.common.command.slotmanagement.GenreCommand
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.user.User

class Genre {
    String name
    GenreStatus status = GenreStatus.UN_PUBLISHED
    Date dateCreated
    String youtubeLink
    User createdBy

    static constraints = {
        name nullable: false, maxSize: 20, unique: true
        youtubeLink nullable: true
        createdBy nullable: true
    }

    Genre() {}

    Genre(GenreCommand genreCommand, User user) {
        this.name = genreCommand.name
        this.youtubeLink = genreCommand.youtubeLink
        this.createdBy = user
    }
}
