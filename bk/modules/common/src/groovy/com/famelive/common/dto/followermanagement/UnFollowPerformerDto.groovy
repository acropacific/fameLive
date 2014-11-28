package com.famelive.common.dto.followermanagement

import com.famelive.common.dto.ResponseDto
import com.famelive.common.followermanagement.Follow

class UnFollowPerformerDto extends ResponseDto {

    Boolean isFollower
    Long totalFollowers

    UnFollowPerformerDto() {}

    UnFollowPerformerDto(Follow follow) {
        this.isFollower = follow ? (follow.isFollower) : false
        this.totalFollowers = follow?.performer?.totalNumberOfFollowers()
    }

    static UnFollowPerformerDto createCommonResponseDto(Follow follow) {
        return new UnFollowPerformerDto(follow)
    }

}
