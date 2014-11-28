package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankGenreNameException
import com.famelive.common.exceptions.slotmanagement.UniqueGenreNameException
import com.famelive.common.slotmanagement.Genre
import grails.validation.Validateable

@Validateable
class GenreCommand extends AuthenticationTokenCommand {

    String name
    String youtubeLink

    static constraints = {
        name nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankGenreNameException()
            } else if (Genre.countByNameIlike(val)) {
                throw new UniqueGenreNameException()
            }
        }
        youtubeLink nullable: true
    }
}
