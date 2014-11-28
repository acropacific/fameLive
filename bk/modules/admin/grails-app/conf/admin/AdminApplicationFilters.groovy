package admin

import com.famelive.admin.command.usermanagement.AdminFetchUserProfileCommand
import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.admin.exception.AdminException
import com.famelive.admin.util.AdminSessionUtils

class AdminApplicationFilters {

    def adminService

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                try {
                    Long userId = AdminSessionUtils.fetchCurrentUserId()
                    String userInfo = userId ? "User#${userId}" : "Anonymous"
                    log.info "(${userInfo}) Params : ${params}"
                    if (userId) {
                        AdminFetchUserProfileCommand fetchUserProfileCommand = new AdminFetchUserProfileCommand(id: AdminSessionUtils.fetchCurrentUserId())
                        AdminUserProfileDto adminUserProfileDto = adminService.fetchUserProfileData(fetchUserProfileCommand)
                        request.setAttribute('admin', adminUserProfileDto)
                    }
                } catch (AdminException adminException) {
                    adminException.printStackTrace(System.out)
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
