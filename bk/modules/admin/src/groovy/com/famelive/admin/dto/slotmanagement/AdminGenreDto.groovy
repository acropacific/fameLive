package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.common.dto.slotmanagement.GenreDto
import com.famelive.common.enums.slotmanagement.GenreStatus

class AdminGenreDto extends AdminResponseDto {
    Long id
    String name
    String youtubeLink
    GenreStatus genreStatus

    AdminGenreDto() {}

    AdminGenreDto(GenreDto genre) {
        this.id = genre.id
        this.name = genre.name
        this.youtubeLink = genre.youtubeLink ?: ""
        this.genreStatus = genre.genreStatus
    }

    static AdminGenreDto createAdminResponseDto(GenreDto genreDto) {
        return new AdminGenreDto(genreDto)
    }

}
