package com.famelive.common.command.usernamagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.BlankEmailException
import com.famelive.common.exceptions.BlankForgotPasswordCodeException
import com.famelive.common.exceptions.EmailNotFoundException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class ValidateForgotPasswordCodeCommand extends RequestCommand {

    String email
    String code

    static constraints = {
        email nullable: true, validator: { val, object ->
            if (!val) {
                throw new BlankEmailException()
            } else if (!User.findByEmail(val)) {
                throw new EmailNotFoundException()
            }

        }
        code nullable: true, validator: {
            val, object ->
                if (!val) {
                    throw new BlankForgotPasswordCodeException()
                }
        }
    }
}
