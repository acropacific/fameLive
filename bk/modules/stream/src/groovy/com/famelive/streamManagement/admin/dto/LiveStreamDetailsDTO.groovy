package com.famelive.streamManagement.admin.dto

import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamInfoResponseCO
import com.famelive.streamManagement.wowza.command.WowzaFetchInStreamStatisticsResponseCO

/**
 * Created by anil on 26/11/14.
 */
class LiveStreamDetailsDTO {
    WowzaFetchInStreamInfoResponseCO inStreamInfoDTO
    WowzaFetchInStreamStatisticsResponseCO inStreamStatisticsDTO
    String eventStatus
    EventInfoDTO eventInfoDTO
    StreamVideoInfoDTO streamVideoInfoDTO
}
