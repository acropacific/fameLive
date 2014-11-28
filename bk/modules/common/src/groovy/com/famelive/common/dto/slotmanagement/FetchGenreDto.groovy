package com.famelive.common.dto.slotmanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.slotmanagement.Genre

class FetchGenreDto extends ResponseDto {
    List<GenreDto> genreDtos

    FetchGenreDto() {}

    FetchGenreDto(List<Genre> genreList) {
        this.genreDtos = populateFetchGenreDto(genreList)
    }

    static FetchGenreDto createCommonResponseDto(List<Genre> genreList) {
        return new FetchGenreDto(genreList)
    }

    static List<GenreDto> populateFetchGenreDto(List<Genre> genreList) {
        List<GenreDto> genreDtos = []
        if (genreList) {
            genreList.each { Genre genre ->
                genreDtos << new GenreDto(genre)
            }
        }
        return genreDtos
    }

}
