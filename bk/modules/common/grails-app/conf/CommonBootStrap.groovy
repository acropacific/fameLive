import com.famelive.common.CommonBootStrapService
import com.famelive.common.user.User
import com.famelive.common.util.CommonMessagesUtil

class CommonBootStrap {
    CommonBootStrapService commonBootStrapService
    def grailsApplication

    def init = { servletContext ->
        if (!CommonMessagesUtil.messageSource) {
            new CommonMessagesUtil().initializeMessageSource(grailsApplication)
        }

        int noOfUsers
        try {
            noOfUsers = User.list().size()
        } catch (Exception e) {
            noOfUsers = -1
        }
        if (noOfUsers < 1) {
            commonBootStrapService.bootstrapUserRoles()
            commonBootStrapService.bootstrapSuperAdmin()
            commonBootStrapService.bootstrapUsers()
            commonBootStrapService.createGenre()
            commonBootStrapService.createSocialAccountTemplate()
            commonBootStrapService.bootstrapWowzaChannels()
            commonBootStrapService.bootStrapStreamManagementConstants()
            commonBootStrapService.bootStrapSlotManagementConstants()
            commonBootStrapService.cleanAndReinitializeSlotManagement()
            commonBootStrapService.bootStrapDummyEventData()
        }
    }

    def destroy = {
    }
}
