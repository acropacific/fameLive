package com.famelive.api.dto.slotmanagement

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.common.dto.slotmanagement.EventDetailDto
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.slotmanagement.GenreDto

@APIResponseClass
public class ApiEventDetailDto extends ApiResponseDto {

    @APIResponseField(include = true)
    public Long eventId
    @APIResponseField(include = true)
    public String name
    @APIResponseField(include = true)
    public String imageName
    @APIResponseField(include = true)
    public String description
    @APIResponseField(include = true)
    public Long duration
    @APIResponseField(include = true)
    public String startTime
    @APIResponseField(include = true)
    public String endTime
    @APIResponseField(include = true)
    public String eventUId
    @APIResponseField(include = true)
    public List<ApiGenreDto> genres

    ApiEventDetailDto() {}

    ApiEventDetailDto(EventDetailDto eventDetailDto) {
        this.eventId = eventDetailDto?.eventId
        this.eventUId=eventDetailDto?.eventUId
        this.name = eventDetailDto?.name
        this.imageName = eventDetailDto?.imageName ?: ""
        this.description = eventDetailDto?.description ?: ""
        this.duration = eventDetailDto?.duration
        this.startTime = eventDetailDto?.startTime?.format(ApiConstants.DATE_TIME_FORMAT)
        this.endTime = eventDetailDto?.endTime?.format(ApiConstants.DATE_TIME_FORMAT)
        this.genres = populateGenreDto(eventDetailDto?.genreDtos)
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
