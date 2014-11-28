package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.exceptions.slotmanagement.BlankGenreIdException
import com.famelive.common.exceptions.slotmanagement.BlankGenreStatusException
import com.famelive.common.exceptions.slotmanagement.GenreNotFoundException
import com.famelive.common.slotmanagement.Genre
import grails.validation.Validateable

@Validateable
class ChangeGenreStatusCommand extends AuthenticationTokenCommand {
    Long genreId
    GenreStatus genreStatus

    static constraints = {
        genreId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankGenreIdException()
            } else if (!Genre.get(val)) {
                throw new GenreNotFoundException()
            }
        }
        genreStatus nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankGenreStatusException()
            }
        }
    }
}
