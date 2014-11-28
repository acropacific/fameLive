package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.slotmanagement.GenreDto

class AdminFetchGenreDto extends AdminResponseDto {

    List<AdminGenreDto> genreDtos

    AdminFetchGenreDto() {}

    AdminFetchGenreDto(FetchGenreDto fetchGenreDto) {
        this.genreDtos = populateGenreDtoList(fetchGenreDto)
    }

    static AdminFetchGenreDto createAdminResponseDto(FetchGenreDto fetchGenreDto) {
        return new AdminFetchGenreDto(fetchGenreDto)
    }

    static List<AdminGenreDto> populateGenreDtoList(FetchGenreDto fetchGenreDto) {
        List<AdminGenreDto> apiGenreDtoList = []
        if (fetchGenreDto?.genreDtos) {
            fetchGenreDto?.genreDtos?.each { GenreDto genreDto ->
                apiGenreDtoList << new AdminGenreDto(genreDto)
            }
        }
        return apiGenreDtoList
    }
}
