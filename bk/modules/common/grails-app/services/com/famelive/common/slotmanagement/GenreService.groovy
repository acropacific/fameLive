package com.famelive.common.slotmanagement

import com.famelive.common.command.slotmanagement.*
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.slotmanagement.GenreDto
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.exceptions.CommonException
import com.famelive.common.user.User

class GenreService {

    def userService

    FetchGenreDto fetchAllPublishedGenre(FetchAllGenreCommand fetchGenreCommand) throws CommonException {
        fetchGenreCommand.validate()
        List<Genre> genreList = Genre.findAllByStatus(GenreStatus.PUBLISHED)
        FetchGenreDto fetchGenreDto = FetchGenreDto.createCommonResponseDto(genreList)
        return fetchGenreDto
    }

    FetchGenreDto fetchAllGenre(FetchAllGenreCommand fetchGenreCommand) throws CommonException {
        fetchGenreCommand.validate()
        userService.findUserById(fetchGenreCommand.id)
        List<Genre> genreList = Genre.list()
        FetchGenreDto fetchGenreDto = FetchGenreDto.createCommonResponseDto(genreList)
        return fetchGenreDto
    }

    GenreDto fetchGenre(FetchGenreCommand fetchGenreCommand) throws CommonException {
        fetchGenreCommand.validate()
        userService.findUserById(fetchGenreCommand.id)
        Genre genre = Genre.get(fetchGenreCommand.genreId)
        GenreDto fetchGenreDto = GenreDto.createCommonResponseDto(genre)
        return fetchGenreDto
    }

    GenreDto saveGenre(GenreCommand genreCommand) throws CommonException {
        genreCommand.validate()
        User user = userService.findUserById(genreCommand.id)
        Genre genre = new Genre(genreCommand, user).save(flush: true, failOnError: true)
        GenreDto genreDto = GenreDto.createCommonResponseDto(genre)
        return genreDto
    }

    GenreDto changeGenreStatus(ChangeGenreStatusCommand changeGenreStatusCommand) throws CommonException {
        changeGenreStatusCommand.validate()
        userService.findUserById(changeGenreStatusCommand.id)
        Genre genre = Genre.get(changeGenreStatusCommand.genreId)
        saveGenreStatus(genre, changeGenreStatusCommand.genreStatus)
        GenreDto genreDto = GenreDto.createCommonResponseDto(genre)
        return genreDto
    }

    void saveGenreStatus(Genre genre, GenreStatus genreStatus) {
        genre.status = genreStatus
        genre.save(flush: true, failOnError: true)
    }

    GenreDto updateGenre(UpdateGenreCommand genreCommand) throws CommonException {
        genreCommand.validate()
        User user = userService.findUserById(genreCommand.id)
        Genre genre = Genre.get(genreCommand.genreId)
        updateGenreDetail(genre, user, genreCommand)
        GenreDto genreDto = GenreDto.createCommonResponseDto(genre)
        return genreDto
    }

    void updateGenreDetail(Genre genre, User user, UpdateGenreCommand genreCommand) {
        genre.createdBy = user
        genre.name = genreCommand.name
        genre.youtubeLink = genreCommand.youtubeLink
        genre.save(flush: true, failOnError: true)
    }


}
