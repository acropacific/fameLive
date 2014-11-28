package com.famelive.admin

import com.famelive.admin.command.slotmanagement.*
import com.famelive.admin.constant.AdminConstants
import com.famelive.admin.dto.slotmanagement.AdminCreateEventDto
import com.famelive.admin.dto.slotmanagement.AdminEventDto
import com.famelive.admin.dto.slotmanagement.AdminFetchEventsDto
import com.famelive.admin.dto.slotmanagement.AdminSlotAvailabilityDto
import com.famelive.admin.exception.AdminException
import com.famelive.admin.util.AdminSessionUtils
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class EventController {

    def adminEventService

    @Secured(['ROLE_SUPER_ADMIN'])
    def list(AdminFetchAllEventCommand adminFetchAllEventCommand) {
        try {
            params.max = Math.min(adminFetchAllEventCommand?.max ?: AdminConstants.EVENT_RESULT_PER_PAGE, AdminConstants.DEFAULT_MAX_PAGINATION)
            AdminFetchAllEventCommand adminFetchAllEventDto = new AdminFetchAllEventCommand(adminFetchAllEventCommand)
            adminFetchAllEventDto.id = AdminSessionUtils.fetchCurrentUserId()
            AdminFetchEventsDto adminFetchEventsDto = adminEventService.fetchAllEvents(adminFetchAllEventDto)
            [adminFetchEventsDto: adminFetchEventsDto, adminFetchAllEventCommand: adminFetchAllEventCommand]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def cancelEventList(AdminFetchAllEventCommand adminFetchAllEventCommand) {
        try {
            params.max = Math.min(adminFetchAllEventCommand?.max ?: AdminConstants.EVENT_RESULT_PER_PAGE, AdminConstants.DEFAULT_MAX_PAGINATION)
            AdminFetchAllEventCommand adminFetchAllEventDto = new AdminFetchAllEventCommand(adminFetchAllEventCommand)
            adminFetchAllEventDto.id = AdminSessionUtils.fetchCurrentUserId()
            AdminFetchEventsDto adminFetchEventsDto = adminEventService.fetchAllCancelEvents(adminFetchAllEventDto)
            [adminFetchEventsDto: adminFetchEventsDto, adminFetchAllEventCommand: adminFetchAllEventCommand]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def create(AdminFetchAllGenreCommand fetchAllGenreCommand) {
        try {
            fetchAllGenreCommand.id = AdminSessionUtils.fetchCurrentUserId()
            AdminCreateEventDto adminCreateEventDto = adminEventService.populateCreateEventVO(fetchAllGenreCommand)
            [adminCreateEventDto: adminCreateEventDto]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def save(AdminEventCommand adminEventCommand) {
        try {
            adminEventCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminEventService.saveEvent(adminEventCommand)
            redirect controller: 'event', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'event', action: 'create'
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def edit(AdminFetchEventCommand adminFetchEventCommand) {
        try {
            adminFetchEventCommand.id = AdminSessionUtils.fetchCurrentUserId()
            AdminEventDto adminEventDto = adminEventService.fetchEvent(adminFetchEventCommand)
            [adminEventDto: adminEventDto]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def update(AdminEditEventCommand adminEditEventCommand) {
        try {
            adminEditEventCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminEventService.editEvent(adminEditEventCommand)
            redirect controller: 'event', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'event', action: 'edit', params: [eventId: adminEditEventCommand?.eventId]
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def cancel(AdminCancelEventCommand adminCancelEventCommand) {
        try {
            adminCancelEventCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminEventService.cancelEvent(adminCancelEventCommand)
            redirect controller: 'event', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'event', action: 'list'
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def slotAvailability() {
        AdminSlotAvailabilityDto adminSlotAvailabilityDto = AdminSlotAvailabilityDto.createAdminResponseDto()
        [checkSlotV0: adminSlotAvailabilityDto]
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def checkSlotAvailability(AdminCheckSlotAvailabilityCommand adminCheckSlotAvailabilityCommand) {
        try {
            adminCheckSlotAvailabilityCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminEventService.checkSlotAvailability(adminCheckSlotAvailabilityCommand)
            flash.message = "Slot is available"
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            flash.message = "No slot is available"
        }
        redirect controller: "event", action: "slotAvailability"
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def viewEventDetail(AdminFetchEventCommand adminFetchEventCommand) {
        try {
            adminFetchEventCommand.id = AdminSessionUtils.fetchCurrentUserId()
            AdminEventDto adminEventDto = adminEventService.fetchEventDetail(adminFetchEventCommand)
            [adminEventVO: adminEventDto]
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
        }
    }
}
