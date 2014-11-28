package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.common.dto.slotmanagement.GenreDto

@APIResponseClass
class ApiGenreDto {

    @APIResponseField(include = true)
    Long id
    @APIResponseField(include = true)
    String name
    @APIResponseField(include = true)
    String youtubeLink

    ApiGenreDto() {}

    ApiGenreDto(GenreDto genreDto) {
        this.id = genreDto?.id
        this.name = genreDto?.name
        this.youtubeLink = genreDto?.youtubeLink ?: ''
    }
}
