package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.exceptions.BlankEmailException
import com.famelive.common.exceptions.UnauthorizedEmailException
import com.famelive.common.exceptions.UniqueEmailException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class ChangeEmailCommand extends AuthenticationTokenCommand {

    String email

    static constraints = {
        email nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEmailException()
            } else if (User.get(obj.id).registrationType != UserRegistrationType.MANUAL) {
                throw new UnauthorizedEmailException()
            } else if (User.countByEmail(val)) {
                throw new UniqueEmailException()
            }
        }
    }
}
