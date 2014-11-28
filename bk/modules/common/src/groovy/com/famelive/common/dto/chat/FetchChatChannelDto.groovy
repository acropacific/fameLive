package com.famelive.common.dto.chat

import com.famelive.common.followermanagement.Follow

class FetchChatChannelDto {

    String eventChannel

    FetchChatChannelDto() {}

    static FetchChatChannelDto createCommonResponseDto(Follow follow) {
        return new FetchChatChannelDto(follow)
    }
}
