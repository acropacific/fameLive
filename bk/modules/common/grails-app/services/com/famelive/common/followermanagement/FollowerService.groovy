package com.famelive.common.followermanagement

import com.famelive.common.command.followermanagement.FetchFollowersCommand
import com.famelive.common.command.followermanagement.FollowPerformerCommand
import com.famelive.common.command.followermanagement.UnFollowPerformerCommand
import com.famelive.common.dto.followermanagement.FollowPerformerDto
import com.famelive.common.dto.followermanagement.UnFollowPerformerDto
import com.famelive.common.dto.usermanagement.UsersDto
import com.famelive.common.exceptions.CommonException
import com.famelive.common.user.User

class FollowerService {

    def userService

    FollowPerformerDto followPerformer(FollowPerformerCommand followPerformerCommand) throws CommonException {
        followPerformerCommand.validate()
        Follow follow = createFollowObject(followPerformerCommand?.id, followPerformerCommand?.performerId, true)
        FollowPerformerDto followPerformerDto = FollowPerformerDto.createCommonResponseDto(follow)
        return followPerformerDto
    }

    UnFollowPerformerDto unFollowPerformer(UnFollowPerformerCommand unFollowPerformerCommand) throws CommonException {
        unFollowPerformerCommand.validate()
        Follow follow = createFollowObject(unFollowPerformerCommand?.id, unFollowPerformerCommand?.performerId, false)
        UnFollowPerformerDto unFollowPerformerDto = UnFollowPerformerDto.createCommonResponseDto(follow)
        return unFollowPerformerDto
    }

    Follow createFollowObject(Long followerId, Long performerId, Boolean isFollower) throws CommonException {
        User follower = userService.findUserById(followerId)
        User performer = userService.findUserById(performerId)
        Follow follow = Follow.findByFollowerAndPerformer(follower, performer) ?: new Follow(performer, follower)
        follow.isFollower = isFollower
        follow.save(flush: true, failOnError: true)
    }

    UsersDto fetchFollowers(FetchFollowersCommand fetchFollowersCommand) throws CommonException {
        fetchFollowersCommand.validate()
        User user = User.get(fetchFollowersCommand?.performerId)
        List<User> followers = user.fetchFollowers()
        UsersDto usersDto = UsersDto.createCommonResponseDto(followers)
        return usersDto
    }
}
