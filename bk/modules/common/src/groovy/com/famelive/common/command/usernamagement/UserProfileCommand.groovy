package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.constant.Constraints
import com.famelive.common.enums.usermanagement.UserType
import com.famelive.common.exceptions.*
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class UserProfileCommand extends AuthenticationTokenCommand {

    String email
    String username
    String fameName
    String mobile
    String imageName

    static constraints = {
        username nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankUserNameException()
            } else if (val.length() > Constraints.USERNAME_MAX_SIZE) {
                throw new UserNameMaxLengthException()
            }
        }
        email nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankEmailException()
            } else if (!User.countByEmail(val)) {
                throw new EmailNotFoundException()
            }
        }
        mobile nullable: true
        imageName nullable: true
        fameName nullable: true, validator: { val, obj ->
            User user = User.findByEmail(obj.email)
            if (user.type == UserType.PERFORMER) {
                if (!val) {
                    throw new BlankFameNameException()
                } else if (val.length() > Constraints.FAMENAME_MAX_SIZE) {
                    throw new FameNameMaxLengthException()
                } else if (User.countByFameNameAndEmailNotEqual(val, obj.email)) {
                    throw new UniqueFameNameException()
                }
            }
        }
    }
}
