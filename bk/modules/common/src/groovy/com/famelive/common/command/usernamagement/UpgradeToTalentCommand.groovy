package com.famelive.common.command.usernamagement

import com.famelive.common.command.AuthenticationTokenCommand
import com.famelive.common.constant.Constraints
import com.famelive.common.exceptions.BlankFameNameException
import com.famelive.common.exceptions.FameNameMaxLengthException
import com.famelive.common.exceptions.UniqueFameNameException
import com.famelive.common.user.User
import grails.validation.Validateable

@Validateable
class UpgradeToTalentCommand extends AuthenticationTokenCommand {
    String fameName

    static constraints = {
        fameName nullable: true, validator: { val, obj ->
            if (!val) {
                throw new BlankFameNameException()
            } else if (val.length() > Constraints.FAMENAME_MAX_SIZE) {
                throw new FameNameMaxLengthException()
            } else if (User.countByFameName(val)) {
                throw new UniqueFameNameException()
            }
        }
    }
}
