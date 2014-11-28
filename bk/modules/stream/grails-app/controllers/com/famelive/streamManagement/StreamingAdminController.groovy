package com.famelive.streamManagement

import com.famelive.streamManagement.admin.StreamingAdminService
import com.famelive.streamManagement.admin.dto.ChannelDetailsDTO
import com.famelive.streamManagement.admin.dto.ChannelInfoDTO
import com.famelive.streamManagement.admin.dto.LiveStreamDetailsDTO
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.springframework.security.core.userdetails.UserDetailsService

//@Secured(['permitAll'])
@Secured(['ROLE_SUPER_ADMIN'])
class StreamingAdminController {
    DefaultGrailsApplication grailsApplication
    UserDetailsService userDetailsService
    SpringSecurityService springSecurityService
    StreamingAdminService streamingAdminService

    def index() {
        println "admin page"
        return redirect(controller: 'streamingAdmin', action: 'fetchChannels')
    }

    def fetchChannels() {
        List<ChannelInfoDTO> channelInfoDTOs = streamingAdminService.fetchChannels()
        render(view: 'fetchChannels', model: [channelInfoDTOs: channelInfoDTOs])
    }

    def fetchChannelDetails() {
        ChannelDetailsDTO channelDetailsDTO = streamingAdminService.fetchChannelDetails(Long.parseLong(params.channelId))
        render(view: 'fetchChannelDetails', model: [channelDetailsDTO: channelDetailsDTO])
    }

    def fetchBookedSlotsForChannel(long channelId) {
    }

    def fetchLiveStreamDetails(long eventStreamInfoId) {

    }

    def fetchLiveStreamDetailsForChannel(long channelId) {
        LiveStreamDetailsDTO liveStreamDetailsDTO = streamingAdminService.fetchLiveStreamDetailsForChannel(Long.parseLong(params.channelId))
        render(view: 'fetchLiveStreamDetailsForChannel', model: [liveStreamDetailsDTO: liveStreamDetailsDTO])
    }
}

