package com.famelive.admin.dto.slotmanagement

import com.famelive.admin.dto.AdminResponseDto
import com.famelive.admin.dto.AdminUserProfileDto
import com.famelive.admin.dto.AdminUsersDto
import com.famelive.common.constant.CommonConstants
import com.famelive.common.dto.slotmanagement.EventDto
import com.famelive.common.dto.slotmanagement.FetchGenreDto
import com.famelive.common.dto.usermanagement.UsersDto

class AdminEventDto extends AdminResponseDto {
    String eventUId
    String name
    String description
    String eventId
    Date startTime
    Date endTime
    AdminFetchGenreDto adminFetchGenreDto
    AdminUsersDto userDetails
    AdminGenreDto genre
    List<Long> durations
    Long duration
    String imageName
    AdminUserProfileDto user

    AdminEventDto() {}

    AdminEventDto(EventDto event) {
        this.name = event?.name
        this.eventId = event?.eventId
        this.description = event?.description
        this.imageName = event?.imageName ?: ""
        this.eventUId = event?.eventUId
        this.duration = event?.duration
        this.startTime = event?.startTime
        this.endTime = event?.endTime
        this.imageName = event?.imageName ?: ""
        this.genre = AdminGenreDto.createAdminResponseDto(event?.genre)
        this.user = AdminUserProfileDto.createAdminResponseDto(event?.user)
    }

    AdminEventDto(EventDto eventDto, FetchGenreDto fetchGenreDto, UsersDto usersDto) {
        this.name = eventDto?.name
        this.eventId = eventDto?.eventId
        this.description = eventDto?.description
        this.startTime = eventDto?.startTime
        this.endTime = eventDto?.endTime
        this.duration = eventDto?.duration
        this.imageName = eventDto?.imageName ?: ""
        this.genre = new AdminGenreDto(eventDto?.genre)
        this.user = new AdminUserProfileDto(eventDto?.user)
        this.adminFetchGenreDto = AdminFetchGenreDto.createAdminResponseDto(fetchGenreDto)
        this.userDetails = AdminUsersDto.createAdminResponseDto(usersDto)
        this.durations = CommonConstants.EVENT_SLOT_DURATIONS as List<Long>
    }

    static AdminEventDto createAdminResponseDto(EventDto eventDto) {
        return new AdminEventDto(eventDto)
    }

    static AdminEventDto createAdminResponseDto(EventDto eventDto, FetchGenreDto fetchGenreDto, UsersDto usersDto) {
        return new AdminEventDto(eventDto, fetchGenreDto, usersDto)
    }
}
