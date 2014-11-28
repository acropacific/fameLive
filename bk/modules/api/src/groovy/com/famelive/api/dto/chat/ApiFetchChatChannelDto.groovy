package com.famelive.api.dto.chat

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.dto.ApiResponseDto
import com.famelive.common.dto.chat.FetchChatChannelDto

@APIResponseClass
class ApiFetchChatChannelDto extends ApiResponseDto {

    @APIResponseField(include = true, parentName = "constraints")
    public String eventChannel

    static ApiFetchChatChannelDto createApiResponseDto(FetchChatChannelDto fetchChatChannelDto) {
        return new ApiFetchChatChannelDto(fetchChatChannelDto)
    }
}
