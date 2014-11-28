package com.famelive.admin

import com.famelive.admin.command.slotmanagement.*
import com.famelive.admin.dto.slotmanagement.AdminFetchGenreDto
import com.famelive.admin.dto.slotmanagement.AdminGenreDto
import com.famelive.admin.exception.AdminException
import com.famelive.admin.util.AdminSessionUtils
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class GenreController {

    def adminGenreService

    @Secured(['ROLE_SUPER_ADMIN'])
    def create() {
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def save(AdminGenreCommand adminGenreCommand) {
        try {
            adminGenreCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminGenreService.saveGenre(adminGenreCommand)
            redirect controller: 'genre', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'genre', action: 'create'
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def edit(AdminFetchGenreCommand adminFetchGenreCommand) {
        adminFetchGenreCommand.id = AdminSessionUtils.fetchCurrentUserId()
        AdminGenreDto adminGenreDto = adminGenreService.fetchGenre(adminFetchGenreCommand)
        [adminGenreDto: adminGenreDto]
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def update(AdminUpdateGenreCommand adminUpdateGenreCommand) {
        try {
            adminUpdateGenreCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminGenreService.updateGenre(adminUpdateGenreCommand)
            redirect controller: 'genre', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'genre', action: 'edit', params: [genreId: adminUpdateGenreCommand?.genreId]
        }
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def list(AdminFetchAllGenreCommand adminFetchAllGenreCommand) {
        adminFetchAllGenreCommand.id = AdminSessionUtils.fetchCurrentUserId()
        AdminFetchGenreDto adminFetchGenreDto = adminGenreService.fetchAllGenre(adminFetchAllGenreCommand)
        [adminFetchGenreDto: adminFetchGenreDto]
    }

    @Secured(['ROLE_SUPER_ADMIN'])
    def changeGenreStatus(AdminChangeGenreStatusCommand adminChangeGenreStatusCommand) {
        try {
            adminChangeGenreStatusCommand.id = AdminSessionUtils.fetchCurrentUserId()
            adminGenreService.changeGenreStatus(adminChangeGenreStatusCommand)
            redirect controller: 'genre', action: 'list'
        } catch (AdminException adminException) {
            adminException.printStackTrace(System.out)
            redirect controller: 'genre', action: 'list'
        }
    }
}


