package com.famelive.admin

import com.famelive.admin.command.slotmanagement.*
import com.famelive.admin.dto.slotmanagement.AdminFetchGenreDto
import com.famelive.admin.dto.slotmanagement.AdminGenreDto
import com.famelive.admin.exception.AdminException
import com.famelive.common.command.slotmanagement.*
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.slotmanagement.GenreDto
import com.famelive.common.exceptions.CommonException

class AdminGenreService {

    def genreService

    void saveGenre(AdminGenreCommand adminGenreCommand) throws AdminException {
        try {
            GenreCommand genreCommand = adminGenreCommand.toRequestCommand()
            genreService.saveGenre(genreCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void updateGenre(AdminUpdateGenreCommand adminUpdateGenreCommand) throws AdminException {
        try {
            UpdateGenreCommand genreCommand = adminUpdateGenreCommand.toRequestCommand()
            genreService.updateGenre(genreCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminGenreDto fetchGenre(AdminFetchGenreCommand adminFetchGenreCommand) throws AdminException {
        try {
            FetchGenreCommand genreCommand = adminFetchGenreCommand.toRequestCommand()
            GenreDto genreDto = genreService.fetchGenre(genreCommand)
            AdminGenreDto adminGenreDto = AdminGenreDto.createAdminResponseDto(genreDto)
            return adminGenreDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    AdminFetchGenreDto fetchAllGenre(AdminFetchAllGenreCommand adminFetchAllGenreCommand) throws AdminException {
        try {
            FetchAllGenreCommand fetchGenreCommand = adminFetchAllGenreCommand.toRequestCommand()
            FetchGenreDto fetchGenreDto = genreService.fetchAllGenre(fetchGenreCommand)
            AdminFetchGenreDto adminFetchGenreDto = AdminFetchGenreDto.createAdminResponseDto(fetchGenreDto)
            return adminFetchGenreDto
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }

    void changeGenreStatus(AdminChangeGenreStatusCommand adminChangeGenreStatusCommand) throws AdminException {
        try {
            ChangeGenreStatusCommand changeGenreStatusCommand = adminChangeGenreStatusCommand.toRequestCommand()
            genreService.changeGenreStatus(changeGenreStatusCommand)
        } catch (CommonException commonException) {
            throw new AdminException(commonException)
        }
    }
}
