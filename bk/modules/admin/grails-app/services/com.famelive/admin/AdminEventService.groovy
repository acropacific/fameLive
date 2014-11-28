package com.famelive.admin

import com.famelive.admin.command.slotmanagement.*
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.dto.slotmanagement.AdminCreateEventDto
import com.famelive.admin.dto.slotmanagement.AdminEventDto
import com.famelive.admin.dto.slotmanagement.AdminFetchEventsDto
import com.famelive.admin.dto.slotmanagement.AdminFetchGenreDto
import com.famelive.admin.exception.AdminException
import com.famelive.admin.util.AdminCloudinaryUtils
import com.famelive.common.command.slotmanagement.*
import com.famelive.common.command.usernamagement.UserSearchCommand
import com.famelive.common.dto.slotmanagement.EventDto
import com.famelive.common.dto.slotmanagement.FetchEventsDto
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.usermanagement.UsersDto
import com.famelive.common.exceptions.CommonException

class AdminEventService {

    def genreService
    def userService
    def eventService
    def adminCloudinaryService
    def grailsApplication

    AdminFetchEventsDto fetchAllEvents(AdminFetchAllEventCommand adminFetchAllEventCommand) throws AdminException {
        try {
            FetchEventsCommand fetchEventsCommand = adminFetchAllEventCommand.toRequestCommand()
            FetchEventsDto fetchEventsDto = eventService.fetchAllActiveEvents(fetchEventsCommand)
            AdminFetchEventsDto adminFetchEventsDto = AdminFetchEventsDto.createAdminResponseDto(fetchEventsDto)
            return adminFetchEventsDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminFetchEventsDto fetchAllCancelEvents(AdminFetchAllEventCommand adminFetchAllEventCommand) throws AdminException {
        try {
            FetchEventsCommand fetchEventsCommand = adminFetchAllEventCommand.toRequestCommand()
            FetchEventsDto fetchEventsDto = eventService.fetchAllCancelEvents(fetchEventsCommand)
            AdminFetchEventsDto adminFetchEventsDto = AdminFetchEventsDto.createAdminResponseDto(fetchEventsDto)
            return adminFetchEventsDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminCreateEventDto populateCreateEventVO(AdminFetchAllGenreCommand adminFetchAllGenreCommand) throws AdminException {
        try {
            FetchAllGenreCommand allGenreCommand = adminFetchAllGenreCommand.toRequestCommand()
            FetchGenreDto fetchGenreDto = genreService.fetchAllGenre(allGenreCommand)
            UserSearchCommand userSearchCommand = adminFetchAllGenreCommand.toUserSearchCommandCommand()
            UsersDto usersDto = userService.fetchUserList(userSearchCommand)
            AdminFetchGenreDto adminFetchGenreDto = AdminFetchGenreDto.createAdminResponseDto(fetchGenreDto)
            AdminCreateEventDto adminCreateEventDto = AdminCreateEventDto.createAdminResponseDto(adminFetchGenreDto, usersDto)
            return adminCreateEventDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminEventDto saveEvent(AdminEventCommand adminEventCommand) throws AdminException {
        try {
            EventCommand eventCommand = adminEventCommand.toRequestCommand()
            EventDto eventDto = eventService.createEvent(eventCommand)
            String imageName = populateEventImageName(eventDto)
            saveEventImage(adminEventCommand?.imageName?.bytes, imageName, eventDto?.eventId, adminEventCommand?.id)
            AdminEventDto adminEventDto = AdminEventDto.createAdminResponseDto(eventDto)
            return adminEventDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    String populateEventImageName(EventDto eventDto) {
        eventDto?.imagePath + "/" + AdminCloudinaryUtils.getRandomImageName()
    }

    void saveEventImage(byte[] image, String imageName, String eventId, Long userId) {
        if (image) {
            uploadEventImage(image, imageName)
            EventImageCommand eventImageCommand = new EventImageCommand(eventId: eventId, imageName: imageName, id: userId)
            eventService.saveEventImage(eventImageCommand)
        }
    }

    void uploadEventImage(byte[] image, String imageName) {
        adminCloudinaryService.uploadImage(image, imageName, AdminConstants.EVENT_IMAGE_TAGS)
    }

    AdminEventDto editEvent(AdminEditEventCommand adminEditEventCommand) throws AdminException {
        try {
            EditEventCommand eventCommand = adminEditEventCommand.toRequestCommand()
            EventDto eventDto = eventService.editEvent(eventCommand)
            saveEventImage(adminEditEventCommand?.imageName?.bytes, eventDto?.imageName, eventDto?.eventId, adminEditEventCommand?.id)
            AdminEventDto adminEventDto = AdminEventDto.createAdminResponseDto(eventDto)
            return adminEventDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminEventDto fetchEvent(AdminFetchEventCommand adminFetchEventCommand) throws AdminException {
        try {
            FetchEventCommand eventCommand = adminFetchEventCommand.toRequestCommand()
            EventDto eventDto = eventService.fetchEvent(eventCommand)
            FetchAllGenreCommand allGenreCommand = new FetchAllGenreCommand(id: adminFetchEventCommand?.id)
            FetchGenreDto fetchGenreDto = genreService.fetchAllGenre(allGenreCommand)
            UserSearchCommand userSearchCommand = adminFetchEventCommand.toUserSearchCommandCommand()
            UsersDto usersDto = userService.fetchUserList(userSearchCommand)
            AdminEventDto adminEventDto = AdminEventDto.createAdminResponseDto(eventDto, fetchGenreDto, usersDto)
            return adminEventDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void cancelEvent(AdminCancelEventCommand adminCancelEventCommand) throws AdminException {
        try {
            CancelEventCommand cancelEventCommand = adminCancelEventCommand.toRequestCommand()
            eventService.cancelEvent(cancelEventCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void checkSlotAvailability(AdminCheckSlotAvailabilityCommand adminCheckSlotAvailabilityCommand) throws AdminException {
        try {
            CheckSlotAvailabilityCommand checkSlotAvailabilityCommand = adminCheckSlotAvailabilityCommand.toRequestCommand()
            eventService.checkSlotAvailability(checkSlotAvailabilityCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminEventDto fetchEventDetail(AdminFetchEventCommand adminFetchEventCommand) throws AdminException {
        try {
            FetchEventCommand eventCommand = adminFetchEventCommand.toRequestCommand()
            EventDto eventDto = eventService.fetchEvent(eventCommand)
            AdminEventDto adminEventDto = AdminEventDto.createAdminResponseDto(eventDto)
            return adminEventDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }
}
