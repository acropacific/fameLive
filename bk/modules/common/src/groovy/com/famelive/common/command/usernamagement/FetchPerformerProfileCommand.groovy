package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.BlankFameIdException
import com.famelive.common.exceptions.PerformerNotFoundException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class FetchPerformerProfileCommand extends AuthenticationTokenCommand {

    String fameId

    static constraints = {
        fameId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankFameIdException()
            } else if (!User.findByFameId(val)) {
                throw new PerformerNotFoundException()
            }
        }
    }
}
