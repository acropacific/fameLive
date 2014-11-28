package com.famelive.admin

import com.famelive.admin.command.AdminUserSearchCommand
import com.famelive.admin.command.usermanagement.AdminFetchUserProfileCommand
import com.famelive.admin.command.usermanagement.AdminUserProfileCommand
import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.admin.dto.AdminUsersDto
import com.famelive.admin.dto.usermanagement.AdminUserInformationDto
import com.famelive.admin.exception.AdminException
import com.famelive.common.command.slotmanagement.FetchEventsCommand
import com.famelive.common.command.usernamagement.FetchUserProfileCommand
import com.famelive.common.command.usernamagement.UserSearchCommand
import com.famelive.common.dto.slotmanagement.FetchEventsDto
import com.famelive.common.dto.usermanagement.UserProfileDto
import com.famelive.common.dto.usermanagement.UsersDto
import com.famelive.common.exceptions.CommonException

class AdminService {

    def userService
    def eventService

    AdminUsersDto fetchUserList(AdminUserSearchCommand adminUserSearchCommand) {
        UserSearchCommand userSearchCommand = adminUserSearchCommand.toRequestCommand()
        UsersDto usersDto = userService.fetchUserList(userSearchCommand)
        AdminUsersDto adminUsersDto = AdminUsersDto.createAdminResponseDto(usersDto)
        return adminUsersDto
    }

    AdminUserProfileDto fetchUserProfileData(AdminFetchUserProfileCommand adminFetchUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminFetchUserProfileCommand.toRequestCommand()
            UserProfileDto userProfileDto = userService.fetchUserProfileData(fetchUserProfileCommand)
            AdminUserProfileDto adminUserProfileDto = AdminUserProfileDto.createAdminResponseDto(userProfileDto)
            return adminUserProfileDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminUserInformationDto fetchUserInformation(AdminUserProfileCommand adminUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminUserProfileCommand.toRequestCommand()
            FetchEventsCommand fetchEventsCommand = adminUserProfileCommand.toFetchEventsCommand()
            UserProfileDto userProfileDto = userService.fetchUserProfileData(fetchUserProfileCommand)
            FetchEventsDto fetchEventsDto = eventService.fetchAllEventsOfUser(fetchEventsCommand)
            AdminUserInformationDto adminUserInformationDto = AdminUserInformationDto.createAdminResponseDto(userProfileDto, fetchEventsDto)
            return adminUserInformationDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void blockOrUnBlockUserAccount(AdminUserProfileCommand adminUserProfileCommand) throws AdminException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = adminUserProfileCommand.toRequestCommand()
            userService.blockOrUnBlockUserAccount(fetchUserProfileCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }
}
