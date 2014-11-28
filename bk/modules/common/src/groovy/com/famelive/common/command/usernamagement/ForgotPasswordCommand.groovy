package com.famelive.common.command.usernamagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.enums.usermanagement.UserRegistrationType
import com.famelive.common.exceptions.BlankEmailException
import com.famelive.common.exceptions.EmailNotFoundException
import com.famelive.common.exceptions.UserAccountBlockException
import com.famelive.common.exceptions.UserInvalidOperationException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class ForgotPasswordCommand extends RequestCommand {
    String email
    static constraints = {
        email nullable: true, validator: { val, object ->
            if (!val) {
                throw new BlankEmailException()
            } else {
                User user = User.findByEmail(val)
                if (!user) {
                    throw new EmailNotFoundException()
                } else if (user.accountLocked) {
                    throw new UserAccountBlockException()
                } else if (user.registrationType != UserRegistrationType.MANUAL) {
                    throw new UserInvalidOperationException()
                }
            }
        }
    }
}
