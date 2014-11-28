package com.famelive.common.command.followermanagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.enums.usermanagement.UserType
import com.famelive.common.exceptions.followermanagement.BlankPerformerIdException
import com.famelive.common.exceptions.followermanagement.InvalidPerformerException
import com.famelive.common.exceptions.followermanagement.InvalidPerformerIdException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class FetchFollowersCommand extends AuthenticationTokenCommand {

    Long performerId

    static constraints = {
        performerId nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankPerformerIdException()
            } else if (!User.get(val)) {
                throw new InvalidPerformerIdException()
            } else if (User.get(val).type == UserType.VIEWER) {
                throw new InvalidPerformerException()
            }
        }
    }
}