package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.slotmanagement.Genre

class GenreDto extends ResponseDto {
    Long id
    String name
    String youtubeLink
    GenreStatus genreStatus

    GenreDto() {}

    GenreDto(Genre genre) {
        this.id = genre.id
        this.name = genre.name
        this.youtubeLink = genre.youtubeLink ?: ""
        this.genreStatus = genre.status
    }

    static GenreDto createCommonResponseDto(Genre genre) {
        return new GenreDto(genre)
    }

}
