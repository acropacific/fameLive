package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.exceptions.BlankVerificationTokenException
import com.famelive.common.exceptions.InvalidVerificationTokenException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class VerifyUserEmailCommand extends AuthenticationTokenCommand {

    String verificationToken

    static constraints = {
        verificationToken nullable: true,validator: {val,obj->
            if (!val) {
                throw new BlankVerificationTokenException()
            } else if (User.get(obj.id).verificationToken!=val) {
                throw new InvalidVerificationTokenException()
            }
        }
    }
}
