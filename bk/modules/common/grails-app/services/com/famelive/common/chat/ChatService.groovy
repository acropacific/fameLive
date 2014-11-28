package com.famelive.common.chat

import com.famelive.common.command.chat.FetchChatChannelCommand
import com.famelive.common.dto.chat.FetchChatChannelDto
import com.famelive.common.exceptions.CommonException
import com.famelive.common.user.User
import grails.transaction.Transactional

@Transactional
class ChatService {

    FetchChatChannelDto fetchChatChannel(FetchChatChannelCommand fetchChatChannelCommand) throws CommonException {

        //TODO need to write logoc here
        User user = User.get(fetchChatChannelCommand?.id)
        FetchChatChannelDto fetchJoinChatChannelDto = new FetchChatChannelDto()
        return fetchJoinChatChannelDto
    }
}
