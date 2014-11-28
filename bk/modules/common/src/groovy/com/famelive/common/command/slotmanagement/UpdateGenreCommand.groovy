package com.famelive.common.command.slotmanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.slotmanagement.BlankGenreIdException
import com.famelive.common.exceptions.slotmanagement.BlankGenreNameException
import com.famelive.common.exceptions.slotmanagement.GenreNotFoundException
import com.famelive.common.exceptions.slotmanagement.UniqueGenreNameException
import com.famelive.common.slotmanagement.Genre
import grails.validation.Validateable

@Validateable
class UpdateGenreCommand extends AuthenticationTokenCommand {

    Long genreId
    String name
    String youtubeLink

    static constraints = {
        genreId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankGenreIdException()
            } else if (!Genre.get(val)) {
                throw new GenreNotFoundException()
            }
        }
        name nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankGenreNameException()
            } else if (Genre.countByNameIlikeAndIdNotEqual(val, obj.genreId)) {
                throw new UniqueGenreNameException()
            }
        }
        youtubeLink nullable: true
    }
}
