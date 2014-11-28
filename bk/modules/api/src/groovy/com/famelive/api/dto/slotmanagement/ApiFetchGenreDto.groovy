package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.slotmanagement.GenreDto

@APIResponseClass
class ApiFetchGenreDto extends ApiResponseDto {

    @APIResponseField(include = true,key = "genres")
    public List<ApiGenreDto> genreDtos

    ApiFetchGenreDto() {}

    ApiFetchGenreDto(FetchGenreDto fetchGenreDto) {
        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.FETCH_GENRE.successCode
        this.genreDtos = populateGenreDto(fetchGenreDto)
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiFetchGenreDto createApiResponseDto(FetchGenreDto fetchGenreDto) {
        return new ApiFetchGenreDto(fetchGenreDto)
    }

    static List<ApiGenreDto> populateGenreDto(FetchGenreDto fetchGenreDto) {
        List<ApiGenreDto> apiGenreDtoList = []
        if (fetchGenreDto?.genreDtos) {
            fetchGenreDto?.genreDtos?.each { GenreDto genreDto ->
                apiGenreDtoList << new ApiGenreDto(genreDto)
            }
        }
        return apiGenreDtoList
    }
}
