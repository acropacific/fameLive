package famelive

import com.famelive.common.user.User

class ApplicationFilters {

    def springSecurityService

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                User user = springSecurityService?.currentUser as User
                String userInfo = user ? "User#${user.id}" : "Anonymous"
                log.info "(${userInfo}) Params : ${params}"
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
