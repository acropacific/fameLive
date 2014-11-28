package com.famelive.common.command.usernamagement

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.BlankEmailException
import com.famelive.common.exceptions.BlankImageNameException
import com.famelive.common.exceptions.EmailNotFoundException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class UserImageCommand extends RequestCommand {
    String email
    String imageName

    static constraints = {
        email nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEmailException()
            } else if (!User.findByEmail(val)) {
                throw new EmailNotFoundException()
            }
        }
        imageName nullable: true
    }
}
