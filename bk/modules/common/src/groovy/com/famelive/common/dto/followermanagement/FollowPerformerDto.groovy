package com.famelive.common.dto.followermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.followermanagement.Follow

class FollowPerformerDto extends ResponseDto {

    Boolean isFollower
    Long totalFollowers

    FollowPerformerDto() {}

    FollowPerformerDto(Follow follow) {
        this.isFollower = follow ? (follow.isFollower) : false
        this.totalFollowers = follow?.performer?.totalNumberOfFollowers()
    }

    static FollowPerformerDto createCommonResponseDto(Follow follow) {
        return new FollowPerformerDto(follow)
    }

}
