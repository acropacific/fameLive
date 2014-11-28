package com.famelive.api

import com.famelive.api.command.chat.ApiFetchChatChannelCommand
import com.famelive.api.command.followermanagement.ApiFetchFollowersCommand
import com.famelive.api.command.followermanagement.ApiFollowPerformerCommand
import com.famelive.api.command.followermanagement.ApiUnFollowPerformerCommand
import com.famelive.api.command.notification.ApiFetchNotificationChannelsCommand
import com.famelive.api.command.search.ApiSearchEventCommand
import com.famelive.api.command.search.ApiSearchPerformerCommand
import com.famelive.api.command.slotmanagement.*
import com.famelive.api.command.usermanagement.*
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.dto.chat.ApiFetchChatChannelDto
import com.famelive.api.dto.configuration.ApiConfigurationDto
import com.famelive.api.dto.followermanagement.ApiFollowPerformerDto
import com.famelive.api.dto.followermanagement.ApiUnFollowPerformerDto
import com.famelive.api.dto.notification.ApiFetchNotificationChannelsDto
import com.famelive.api.dto.search.ApiFetchFollowersDto
import com.famelive.api.dto.search.ApiSearchPerformerDto
import com.famelive.api.dto.slotmanagement.*
import com.famelive.api.dto.socialtemplate.ApiSocialTemplateDto
import com.famelive.api.dto.usermanagement.*
import com.famelive.api.enums.ApiUserRegistrationType
import com.famelive.api.exceptions.*
import com.famelive.api.exceptions.followermanagement.ApiBlankPerformerIdException
import com.famelive.api.exceptions.followermanagement.ApiInvalidFollowDetailException
import com.famelive.api.exceptions.followermanagement.ApiInvalidPerformerException
import com.famelive.api.exceptions.followermanagement.ApiInvalidPerformerIdException
import com.famelive.api.exceptions.rule.ApiCheckConflictUserEventRuleException
import com.famelive.api.exceptions.rule.ApiCheckPublishedGenreRuleException
import com.famelive.api.exceptions.rule.ApiCheckSlotAvailabilityRuleException
import com.famelive.api.exceptions.slotmanagement.*
import com.famelive.common.command.chat.FetchChatChannelCommand
import com.famelive.common.command.followermanagement.FetchFollowersCommand
import com.famelive.common.command.followermanagement.FollowPerformerCommand
import com.famelive.common.command.followermanagement.UnFollowPerformerCommand
import com.famelive.common.command.notification.FetchNotificationChannelsCommand
import com.famelive.common.command.slotmanagement.*
import com.famelive.common.command.template.FetchSocialTemplateCommand
import com.famelive.common.command.usernamagement.*
import com.famelive.common.dto.chat.FetchChatChannelDto
import com.famelive.common.dto.followermanagement.FollowPerformerDto
import com.famelive.common.dto.followermanagement.UnFollowPerformerDto
import com.famelive.common.dto.notification.FetchNotificationChannelsDto
import com.famelive.common.dto.slotmanagement.*
import com.famelive.common.dto.socialtemplate.SocialTemplateDto
import com.famelive.common.dto.usermanagement.*
import com.famelive.common.exceptions.*
import com.famelive.common.exceptions.followermanagement.BlankPerformerIdException
import com.famelive.common.exceptions.followermanagement.InvalidFollowDetailException
import com.famelive.common.exceptions.followermanagement.InvalidPerformerException
import com.famelive.common.exceptions.followermanagement.InvalidPerformerIdException
import com.famelive.common.exceptions.rule.CheckConflictUserEventRuleException
import com.famelive.common.exceptions.rule.CheckPublishedGenreRuleException
import com.famelive.common.exceptions.rule.CheckSlotAvailabilityRuleException
import com.famelive.common.exceptions.slotmanagement.*
import com.famelive.common.user.User

class ApiService {

    def userService
    def apiSecurityService
    def genreService
    def eventService
    def springSecurityService
    def followerService
    def templateService
    def notificationService
    def chatService

    ApiConfigurationDto configuration() {
        return ApiConfigurationDto.createApiResponseDto(userService.fetchConfiguration())
    }

    ApiRegistrationDto registerUser(ApiRegistrationCommand registrationCommand) throws ApiException {
        try {
            return ApiRegistrationDto.createApiResponseDto(userService.registerUser(registrationCommand.toRequestCommand()))
        } catch (BlankUserNameException commonBlankUserNameException) {
            throw new ApiBlankUserNameException(commonBlankUserNameException)
        } catch (UserNameMaxLengthException commonUserNameMaxSizeException) {
            throw new ApiUserNameMaxSizeException(commonUserNameMaxSizeException)
        } catch (BlankPasswordException blankPasswordException) {
            throw new ApiBlankPasswordException(blankPasswordException)
        } catch (PasswordPatternException blankPasswordException) {
            throw new ApiPasswordPatternException(blankPasswordException)
        } catch (PasswordMinLengthException passwordMinLengthException) {
            throw new ApiPasswordMinLengthException(passwordMinLengthException)
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (UniqueEmailException uniqueEmailException) {
            throw new ApiUniqueEmailException(uniqueEmailException)
        } catch (BlankMobileException blankMobileException) {
            throw new ApiBlankMobileException(blankMobileException)
        } catch (MobileNumberLengthException mobileNumberLengthException) {
            throw new ApiMobileNumberLengthException(mobileNumberLengthException)
        } catch (MobileNumberInvalidException mobileNumberInvalidException) {
            throw new ApiMobileNumberInvalidException(mobileNumberInvalidException)
        } catch (BlankFameNameException blankFameNameException) {
            throw new ApiBlankFameNameException(blankFameNameException)
        } catch (FameNameMaxLengthException fameNameMaxLengthException) {
            throw new ApiFameNameMaxLengthException(fameNameMaxLengthException)
        } catch (UniqueFameNameException uniqueFameNameException) {
            throw new ApiUniqueFameNameException(uniqueFameNameException)
        } catch (BlankUserRegistrationTypeException blankUserRegistrationTypeException) {
            throw new ApiBlankUserRegistrationTypeException(blankUserRegistrationTypeException)
        } catch (BlankUserTypeException blankUserTypeException) {
            throw new ApiBlankUserTypeException(blankUserTypeException)
        }
    }

    ApiResponseDto logout(ApiLogoutCommand logoutCommand) {
        ApiResponseDto responseDto
        try {
            boolean isLoggedOut = apiSecurityService.logoutUser(logoutCommand.request, logoutCommand.response)
            if (isLoggedOut) {
                responseDto = new ApiLogoutSuccessDto()
            } else {
                responseDto = new ApiLogoutFailedDto()
            }
        } catch (ApiException exception) {
            responseDto = new ApiLogoutFailedDto(exception)
        }
        return responseDto
    }

    ApiResponseDto login(ApiLoginCommand loginCommand) throws ApiException {
        ApiResponseDto responseDto
        try {
            User user = User.findByEmail(loginCommand.email)
            if (!user) {
                throw new ApiInvalidLoginCredentialException()
            }
            if (user.accountLocked) {
                throw new ApiUserAccountBlockException()
            }
            String password = fetchPassword(loginCommand, user)
            boolean isAuthorized = apiSecurityService.authenticateUser(loginCommand.request, loginCommand.email, password)
            if (isAuthorized) {
                responseDto = new ApiLoginSuccessDto()
                responseDto.access_token = apiSecurityService.loginAsUser(User.findByEmail(loginCommand.email))
                responseDto.email = user.email
                responseDto.userId = user.id
                responseDto.roles = springSecurityService.authentication.authorities as String
            } else {
                responseDto = new ApiLoginFailedDto()
            }
        } catch (ApiUserAccountBlockException apiUserAccountBlockException) {
            responseDto = new ApiResponseDto(apiUserAccountBlockException)
        } catch (ApiException exception) {
            responseDto = new ApiLoginFailedDto(exception)
        }
        responseDto
    }

    String fetchPassword(ApiLoginCommand loginCommand, User user) {
        (loginCommand.medium == ApiUserRegistrationType.MANUAL) ? loginCommand?.password : userService.generatePassword(user?.username)
    }

    ApiChangePasswordDto changePassword(ApiChangePasswordCommand changePasswordCommand) throws ApiException {
        try {
            return ApiChangePasswordDto.createApiResponseDto(userService.changePassword(changePasswordCommand.toRequestCommand()))
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (BlankPasswordException blankPasswordException) {
            throw new ApiBlankPasswordException(blankPasswordException)
        } catch (PasswordPatternException blankPasswordException) {
            throw new ApiPasswordPatternException(blankPasswordException)
        } catch (PasswordMinLengthException passwordMinLengthException) {
            throw new ApiPasswordMinLengthException(passwordMinLengthException)
        } catch (BlankNewPasswordException blankNewPasswordException) {
            throw new ApiBlankNewPasswordException(blankNewPasswordException)
        } catch (PasswordNotEqualException passwordNotEqualException) {
            throw new ApiPasswordNotEqualException(passwordNotEqualException)
        } catch (NewPasswordConfirmPasswordNotSameException newPasswordConfirmPasswordNotSameException) {
            throw new ApiNewPasswordConfirmPasswordNotSameException(newPasswordConfirmPasswordNotSameException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiForgotPasswordDto forgotPassword(ApiForgotPasswordCommand forgotPasswordCommand) throws ApiException {
        try {
            return ApiForgotPasswordDto.createApiResponseDto(userService.forgotPassword(forgotPasswordCommand.toRequestCommand()))
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (EmailNotFoundException emailNotFoundException) {
            throw new ApiEmailNotFoundException(emailNotFoundException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (UserAccountBlockException userAccountBlockException) {
            throw new ApiUserAccountBlockException(userAccountBlockException)
        } catch (UserInvalidOperationException userInvalidOperationException) {
            throw new ApiUserInvalidOperationException(userInvalidOperationException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException.message)
        }
    }

    ApiUserProfileDto updateUserProfile(ApiUserProfileCommand userProfileCommand) throws ApiException {
        try {
            return ApiUserProfileDto.createApiResponseDto(userService.updateUserProfile(userProfileCommand.toRequestCommand()))
        } catch (BlankUserNameException commonBlankUserNameException) {
            throw new ApiBlankUserNameException(commonBlankUserNameException)
        } catch (UserNameMaxLengthException commonUserNameMaxSizeException) {
            throw new ApiUserNameMaxSizeException(commonUserNameMaxSizeException)
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (EmailNotFoundException emailNotFoundException) {
            throw new ApiEmailNotFoundException(emailNotFoundException)
        } catch (BlankMobileException blankMobileException) {
            throw new ApiBlankMobileException(blankMobileException)
        } catch (MobileNumberLengthException mobileNumberLengthException) {
            throw new ApiMobileNumberLengthException(mobileNumberLengthException)
        } catch (MobileNumberInvalidException mobileNumberInvalidException) {
            throw new ApiMobileNumberInvalidException(mobileNumberInvalidException)
        } catch (BlankFameNameException blankFameNameException) {
            throw new ApiBlankFameNameException(blankFameNameException)
        } catch (FameNameMaxLengthException fameNameMaxLengthException) {
            throw new ApiFameNameMaxLengthException(fameNameMaxLengthException)
        } catch (UniqueFameNameException uniqueFameNameException) {
            throw new ApiUniqueFameNameException(uniqueFameNameException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFetchUserProfileDto fetchUserProfileData(ApiFetchUserProfileCommand apiFetchUserProfileCommand) throws ApiException {
        try {
            FetchUserProfileCommand fetchUserProfileCommand = apiFetchUserProfileCommand.toRequestCommand()
            UserProfileDto userProfileDto = userService.fetchUserProfileData(fetchUserProfileCommand)
            ApiFetchUserProfileDto apiFetchUserProfileDto = ApiFetchUserProfileDto.createApiResponseDto(userProfileDto)
            return apiFetchUserProfileDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFetchSocialAccountDto fetchSocialAccounts(ApiFetchUserSocialAccountCommand apiFetchUserSocialAccountCommand) throws ApiException {
        try {
            FetchUserSocialAccountCommand fetchUserSocialAccountCommand = apiFetchUserSocialAccountCommand.toRequestCommand()
            FetchSocialAccountDto fetchSocialAccountDto = userService.fetchSocialAccounts(fetchUserSocialAccountCommand)
            ApiFetchSocialAccountDto apiFetchSocialAccountDto = ApiFetchSocialAccountDto.createApiResponseDto(fetchSocialAccountDto)
            return apiFetchSocialAccountDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiValidateForgotPasswordCodeDto validateForgotPasswordCode(ApiValidateForgotPasswordCodeCommand apiValidateForgotPasswordCodeCommand) throws ApiException {
        try {
            ValidateForgotPasswordCodeCommand forgotPasswordCodeCommand = apiValidateForgotPasswordCodeCommand.toRequestCommand()
            ValidateForgotPasswordCodeDto validateForgotPasswordCodeDto = userService.validateForgotPasswordCode(forgotPasswordCodeCommand)
            String successToken = apiSecurityService.loginAsUser(validateForgotPasswordCodeDto?.user)
            ValidateForgotPasswordCodeDto validateForgotPasswordCodeDtoWithSuccessToken = ValidateForgotPasswordCodeDto.createCommonResponseDto(validateForgotPasswordCodeDto?.user, successToken)
            ApiValidateForgotPasswordCodeDto apiValidateForgotPasswordCodeDto = ApiValidateForgotPasswordCodeDto.createApiResponseDto(validateForgotPasswordCodeDtoWithSuccessToken)
            return apiValidateForgotPasswordCodeDto
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (EmailNotFoundException emailNotFoundException) {
            throw new ApiEmailNotFoundException(emailNotFoundException)
        } catch (BlankForgotPasswordCodeException blankForgotPasswordCodeException) {
            throw new ApiBlankForgotPasswordCodeException(blankForgotPasswordCodeException)
        } catch (InvalidForgotPasswordCodeException invalidForgotPasswordCodeException) {
            throw new ApiInvalidForgotPasswordCodeException(invalidForgotPasswordCodeException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiUpdateForgotPasswordDto updateForgotPassword(ApiUpdateForgotPasswordCommand apiUpdateForgotPasswordCommand) throws ApiException {
        try {
            apiUpdateForgotPasswordCommand.sessionEmail = springSecurityService?.currentUser?.email
            UpdateForgotPasswordCommand updateForgotPasswordCommand = apiUpdateForgotPasswordCommand.toRequestCommand()
            UpdateForgotPasswordDto updateForgotPasswordDto = userService.updateForgotPassword(updateForgotPasswordCommand)
            ApiUpdateForgotPasswordDto apiUpdateForgotPasswordDto = ApiUpdateForgotPasswordDto.createApiResponseDto(updateForgotPasswordDto)
            return apiUpdateForgotPasswordDto
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (EmailNotFoundException emailNotFoundException) {
            throw new ApiEmailNotFoundException(emailNotFoundException)
        } catch (BlankPasswordException blankPasswordException) {
            throw new ApiBlankPasswordException(blankPasswordException)
        } catch (PasswordNotEqualException passwordNotEqualException) {
            throw new ApiPasswordNotEqualException(passwordNotEqualException)
        } catch (UnauthorizedEmailException unauthorizedEmailException) {
            throw new ApiUnauthorizedEmailException(unauthorizedEmailException)
        } catch (PasswordMinLengthException passwordMinLengthException) {
            throw new ApiPasswordMinLengthException(passwordMinLengthException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiUserImageDto saveUserImageName(ApiUserImageCommand apiUserImageCommand) throws ApiException {
        try {
            UserImageCommand imageCommand = apiUserImageCommand.toRequestCommand()
            UserImageDto userImageDto = userService.saveUserImageName(imageCommand)
            ApiUserImageDto apiUserImageDto = ApiUserImageDto.createApiResponseDto(userImageDto)
            return apiUserImageDto
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (EmailNotFoundException emailNotFoundException) {
            throw new ApiEmailNotFoundException(emailNotFoundException)
        } catch (BlankImageNameException blankImageNameException) {
            throw new ApiBlankEmailException(blankImageNameException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFameNameAvailabilityDto isFameNameAvailable(ApiCheckFameNameAvailabilityCommand apiCheckFameNameAvailabilityCommand) throws ApiException {
        try {
            FameNameCommand fameNameCommand = apiCheckFameNameAvailabilityCommand.toRequestCommand()
            FameNameAvailabilityDto fameNameAvailabilityDto = userService.isFameNameAvailable(fameNameCommand)
            ApiFameNameAvailabilityDto apiFameNameAvailabilityDto = ApiFameNameAvailabilityDto.createApiResponseDto(fameNameAvailabilityDto)
            return apiFameNameAvailabilityDto
        } catch (BlankFameNameException blankFameNameException) {
            throw new ApiBlankFameNameException(blankFameNameException)
        } catch (FameNameMaxLengthException fameNameMaxLengthException) {
            throw new ApiFameNameMaxLengthException(fameNameMaxLengthException)
        } catch (UniqueFameNameException uniqueFameNameException) {
            throw new ApiUniqueFameNameException(uniqueFameNameException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchGenreDto fetchAllPublishedGenre(ApiFetchGenreCommand apiFetchGenreCommand) throws ApiException {
        try {
            FetchAllGenreCommand fetchGenreCommand = apiFetchGenreCommand.toRequestCommand()
            FetchGenreDto fetchGenreDto = genreService.fetchAllPublishedGenre(fetchGenreCommand)
            ApiFetchGenreDto apiFetchGenreDto = ApiFetchGenreDto.createApiResponseDto(fetchGenreDto)
            return apiFetchGenreDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiEventDto createEvent(ApiEventCommand apiEventCommand) throws ApiException {
        try {
            EventCommand eventCommand = apiEventCommand.toRequestCommand()
            EventDto eventDto = eventService.createEvent(eventCommand)
            ApiEventDto apiEventDto = ApiEventDto.createApiResponseDto(eventDto)
            return apiEventDto
        } catch (BlankEventNameException blankEventNameException) {
            throw new ApiBlankEventNameException(blankEventNameException)
        } catch (EventNameMaxLengthException eventNameMaxLengthException) {
            throw new ApiEventNameMaxLengthException(eventNameMaxLengthException)
        } catch (EventDescriptionMaxLengthException eventDescriptionMaxLengthException) {
            throw new ApiEventDescriptionMaxLengthException(eventDescriptionMaxLengthException)
        } catch (BlankEventStartTimeException blankEventStartTimeException) {
            throw new ApiBlankEventStartTimeException(blankEventStartTimeException)
        } catch (BlankEventGenreException blankEventGenreException) {
            throw new ApiBlankEventGenreException(blankEventGenreException)
        } catch (BlankEventDurationException blankEventDurationException) {
            throw new ApiBlankEventDurationException(blankEventDurationException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CheckSlotAvailabilityRuleException checkSlotAvailabilityRuleException) {
            throw new ApiCheckSlotAvailabilityRuleException(checkSlotAvailabilityRuleException)
        } catch (CheckConflictUserEventRuleException checkConflictUserEventRuleException) {
            throw new ApiCheckConflictUserEventRuleException(checkConflictUserEventRuleException)
        } catch (CheckPublishedGenreRuleException checkPublishedGenreRuleException) {
            throw new ApiCheckPublishedGenreRuleException(checkPublishedGenreRuleException)
        }
    }

    ApiEditEventDto editEvent(ApiEditEventCommand apiEventCommand) throws ApiException {
        try {
            EditEventCommand eventCommand = apiEventCommand.toRequestCommand()
            EventDto eventDto = eventService.editEvent(eventCommand)
            ApiEditEventDto apiEventDto = ApiEditEventDto.createApiResponseDto(eventDto)
            return apiEventDto
        } catch (BlankEventNameException blankEventNameException) {
            throw new ApiBlankEventNameException(blankEventNameException)
        } catch (EventNameMaxLengthException eventNameMaxLengthException) {
            throw new ApiEventNameMaxLengthException(eventNameMaxLengthException)
        } catch (EventDescriptionMaxLengthException eventDescriptionMaxLengthException) {
            throw new ApiEventDescriptionMaxLengthException(eventDescriptionMaxLengthException)
        } catch (BlankEventStartTimeException blankEventStartTimeException) {
            throw new ApiBlankEventStartTimeException(blankEventStartTimeException)
        } catch (BlankEventGenreException blankEventGenreException) {
            throw new ApiBlankEventGenreException(blankEventGenreException)
        } catch (BlankEventDurationException blankEventDurationException) {
            throw new ApiBlankEventDurationException(blankEventDurationException)
        } catch (BlankEventIdException blankEventIdException) {
            throw new ApiBlankEventIdException(blankEventIdException)
        } catch (EventNotFoundException eventNotFoundException) {
            throw new ApiEventNotFoundException(eventNotFoundException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CheckSlotAvailabilityRuleException checkSlotAvailabilityRuleException) {
            throw new ApiCheckSlotAvailabilityRuleException(checkSlotAvailabilityRuleException)
        } catch (CheckConflictUserEventRuleException checkConflictUserEventRuleException) {
            throw new ApiCheckConflictUserEventRuleException(checkConflictUserEventRuleException)
        } catch (CheckPublishedGenreRuleException checkPublishedGenreRuleException) {
            throw new ApiCheckPublishedGenreRuleException(checkPublishedGenreRuleException)
        }
    }

    ApiEventImageDto saveEventImage(ApiEventImageCommand apiEventImageCommand) throws ApiException {
        try {
            EventImageCommand imageCommand = apiEventImageCommand.toRequestCommand()
            EventImageDto eventImageDto = eventService.saveEventImage(imageCommand)
            ApiEventImageDto apiEventImageDto = ApiEventImageDto.createApiResponseDto(eventImageDto)
            return apiEventImageDto
        } catch (BlankEventImageNameException blankEventImageNameException) {
            throw new ApiBlankEventImageNameException(blankEventImageNameException)
        } catch (BlankEventIdException blankEventIdException) {
            throw new ApiBlankEventIdException(blankEventIdException)
        } catch (EventNotFoundException eventNotFoundException) {
            throw new ApiEventNotFoundException(eventNotFoundException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiCancelEventDto cancelEvent(ApiCancelEventCommand apiCancelEventCommand) throws ApiException {
        try {
            CancelEventCommand cancelEventCommand = apiCancelEventCommand.toRequestCommand()
            CancelEventDto cancelEventDto = eventService.cancelEvent(cancelEventCommand)
            ApiCancelEventDto apiCancelEventDto = ApiCancelEventDto.createApiResponseDto(cancelEventDto)
            return apiCancelEventDto
        } catch (BlankEventIdException blankEventIdException) {
            throw new ApiBlankEventIdException(blankEventIdException)
        } catch (EventNotFoundException eventNotFoundException) {
            throw new ApiEventNotFoundException(eventNotFoundException)
        } catch (EventCancelTimeInvalidException eventCancelTimeInvalidException) {
            throw new ApiEventCancelTimeInvalidException(eventCancelTimeInvalidException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFetchEventsDto fetchAllEventsOfUser(ApiFetchUserEventListCommand apiFetchEventListCommand) throws ApiException {
        try {
            FetchEventsCommand fetchEventsCommand = apiFetchEventListCommand.toRequestCommand()
            FetchEventsDto fetchEventsDto = eventService.fetchAllEventsOfUser(fetchEventsCommand)
            ApiFetchEventsDto apiFetchEventsDto = ApiFetchEventsDto.createApiResponseDto(fetchEventsDto)
            return apiFetchEventsDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFetchEventsDto fetchEventList(ApiFetchEventListCommand apiFetchEventListCommand) throws ApiException {
        try {
            EventListCommand eventListCommand = apiFetchEventListCommand.toRequestCommand()
            FetchEventsDto fetchEventsDto = eventService.fetchAllEvents(eventListCommand)
            ApiFetchEventsDto apiFetchEventsDto = ApiFetchEventsDto.createApiResponseDto(fetchEventsDto)
            return apiFetchEventsDto
        } catch (BlankEventStatusException blankEventStatusException) {
            throw new ApiBlankEventStatusException(blankEventStatusException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiEventDetailsDto fetchEventDetails(ApiFetchEventDetailsCommand apiFetchEventDetailsCommand) throws ApiException {
        try {
            EventDetailsCommand eventDetailsCommand = apiFetchEventDetailsCommand.toRequestCommand()
            EventDetailsDto eventDetailsDto = eventService.fetchEventDetails(eventDetailsCommand)
            ApiEventDetailsDto apiEventDetailsDto = ApiEventDetailsDto.createApiResponseDto(eventDetailsDto)
            return apiEventDetailsDto
        } catch (BlankEventIdException blankEventIdException) {
            throw new ApiBlankEventIdException(blankEventIdException)
        } catch (EventNotFoundException eventNotFoundException) {
            throw new ApiEventNotFoundException(eventNotFoundException)
        } catch (EventStreamInfoNotFoundException eventStreamInfoNotFoundException) {
            throw new ApiEventStreamInfoNotFoundException(eventStreamInfoNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiAddSocialAccountDto addSocialAccount(ApiUserSocialAccountCommand apiAddUserSocialAccountCommand) throws ApiException {
        try {
            UserSocialAccountCommand addUserSocialAccountCommand = apiAddUserSocialAccountCommand.toRequestCommand()
            SocialAccountDto addSocialAccountDto = userService.addSocialAccount(addUserSocialAccountCommand)
            ApiAddSocialAccountDto apiAddSocialAccountDto = ApiAddSocialAccountDto.createApiResponseDto(addSocialAccountDto)
            return apiAddSocialAccountDto
        } catch (BlankSocialAccountException blankSocialAccountException) {
            throw new ApiBlankSocialAccountException(blankSocialAccountException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiRemoveSocialAccountDto removeSocialAccount(ApiUserSocialAccountCommand apiAddUserSocialAccountCommand) throws ApiException {
        try {
            UserSocialAccountCommand addUserSocialAccountCommand = apiAddUserSocialAccountCommand.toRequestCommand()
            SocialAccountDto addSocialAccountDto = userService.removeSocialAccount(addUserSocialAccountCommand)
            ApiRemoveSocialAccountDto apiRemoveSocialAccountDto = ApiRemoveSocialAccountDto.createApiResponseDto(addSocialAccountDto)
            return apiRemoveSocialAccountDto
        } catch (BlankSocialAccountException blankSocialAccountException) {
            throw new ApiBlankSocialAccountException(blankSocialAccountException)
        } catch (InvalidUserSocialAccountException invalidUserSocialAccountException) {
            throw new ApiInvalidUserSocialAccountException(invalidUserSocialAccountException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiFollowPerformerDto follow(ApiFollowPerformerCommand apiFollowPerformerCommand) throws ApiException {
        try {
            FollowPerformerCommand followPerformerCommand = apiFollowPerformerCommand.toRequestCommand()
            FollowPerformerDto followPerformerDto = followerService.followPerformer(followPerformerCommand)
            ApiFollowPerformerDto apiFollowPerformerDto = ApiFollowPerformerDto.createApiResponseDto(followPerformerDto)
            return apiFollowPerformerDto
        } catch (InvalidPerformerIdException invalidPerformerIdException) {
            throw new ApiInvalidPerformerIdException(invalidPerformerIdException)
        } catch (BlankPerformerIdException blankPerformerIdException) {
            throw new ApiBlankPerformerIdException(blankPerformerIdException)
        } catch (InvalidPerformerException invalidPerformerException) {
            throw new ApiInvalidPerformerException(invalidPerformerException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiUnFollowPerformerDto unFollow(ApiUnFollowPerformerCommand apiUnFollowPerformerCommand) throws ApiException {
        try {
            UnFollowPerformerCommand unFollowPerformerCommand = apiUnFollowPerformerCommand.toRequestCommand()
            UnFollowPerformerDto unFollowPerformerDto = followerService.unFollowPerformer(unFollowPerformerCommand)
            ApiUnFollowPerformerDto apiUnFollowPerformerDto = ApiUnFollowPerformerDto.createApiResponseDto(unFollowPerformerDto)
            return apiUnFollowPerformerDto
        } catch (InvalidPerformerIdException invalidPerformerIdException) {
            throw new ApiInvalidPerformerIdException(invalidPerformerIdException)
        } catch (BlankPerformerIdException blankPerformerIdException) {
            throw new ApiBlankPerformerIdException(blankPerformerIdException)
        } catch (InvalidFollowDetailException invalidFollowDetailException) {
            throw new ApiInvalidFollowDetailException(invalidFollowDetailException)
        } catch (InvalidPerformerException invalidPerformerException) {
            throw new ApiInvalidPerformerException(invalidPerformerException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        }
    }

    ApiSearchPerformerDto searchPerformer(ApiSearchPerformerCommand apiSearchPerformerCommand) throws ApiException {
        try {
            UserSearchCommand searchPerformerCommand = apiSearchPerformerCommand.toRequestCommand()
            UsersDto usersDto = userService.fetchUserList(searchPerformerCommand)
            ApiSearchPerformerDto apiSearchPerformerDto = ApiSearchPerformerDto.createApiResponseDto(usersDto)
            return apiSearchPerformerDto
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchPerformerProfileDto fetchPerformerDetail(ApiFetchPerformerProfileCommand apiFetchPerformerProfileCommand) throws ApiException {
        try {
            FetchPerformerProfileCommand fetchPerformerProfileCommand = apiFetchPerformerProfileCommand.toRequestCommand()
            UserProfileDto userProfileDto = userService.fetchPerformerDetail(fetchPerformerProfileCommand)
            ApiFetchPerformerProfileDto apiUserProfileDto = ApiFetchPerformerProfileDto.createApiResponseDto(userProfileDto)
            return apiUserProfileDto
        } catch (PerformerNotFoundException performerNotFoundException) {
            throw new ApiPerformerNotFoundException(performerNotFoundException)
        } catch (BlankFameIdException blankFameIdException) {
            throw new ApiBlankFameIdException(blankFameIdException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchEventsDto searchEvent(ApiSearchEventCommand apiSearchEventCommand) throws ApiException {
        try {
            FetchEventsCommand fetchEventsCommand = apiSearchEventCommand.toRequestCommand()
            FetchEventsDto fetchEventsDto = eventService.fetchAllActiveEvents(fetchEventsCommand)
            ApiFetchEventsDto apiFetchEventsDto = ApiFetchEventsDto.createApiResponseDto(fetchEventsDto)
            return apiFetchEventsDto
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchNotificationChannelsDto fetchNotificationChannels(ApiFetchNotificationChannelsCommand apiFetchNotificationChannelsCommand) throws ApiException {
        try {
            FetchNotificationChannelsCommand fetchNotificationChannelsCommand = apiFetchNotificationChannelsCommand.toRequestCommand()
            FetchNotificationChannelsDto fetchNotificationChannelsDto = notificationService.fetchNotificationChannels(fetchNotificationChannelsCommand)
            ApiFetchNotificationChannelsDto apiFetchNotificationChannelsDto = ApiFetchNotificationChannelsDto.createApiResponseDto(fetchNotificationChannelsDto)
            return apiFetchNotificationChannelsDto
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchChatChannelDto fetchChatChannel(ApiFetchChatChannelCommand apiFetchChatChannelCommand) throws ApiException {
        try {
            FetchChatChannelCommand fetchChatChannelCommand = apiFetchChatChannelCommand.toRequestCommand()
            FetchChatChannelDto fetchChatChannelDto = chatService.fetchChatChannel(fetchChatChannelCommand)
            ApiFetchChatChannelDto apiFetchChatChannelDto = ApiFetchChatChannelDto.createApiResponseDto(fetchChatChannelDto)
            return apiFetchChatChannelDto
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiFetchFollowersDto fetchFollowers(ApiFetchFollowersCommand apiFollowersCommand) throws ApiException {
        try {
            FetchFollowersCommand fetchFollowersCommand = apiFollowersCommand.toRequestCommand()
            UsersDto usersDto = followerService.fetchFollowers(fetchFollowersCommand)
            ApiFetchFollowersDto apiFetchFollowersDto = ApiFetchFollowersDto.createApiResponseDto(usersDto)
            return apiFetchFollowersDto
        } catch (BlankPerformerIdException blankPerformerIdException) {
            throw new ApiBlankPerformerIdException(blankPerformerIdException)
        } catch (InvalidPerformerException invalidPerformerException) {
            throw new ApiInvalidPerformerException(invalidPerformerException)
        } catch (InvalidPerformerIdException invalidPerformerIdException) {
            throw new ApiInvalidPerformerIdException(invalidPerformerIdException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiSocialTemplateDto fetchSocialTemplate(ApiFetchSocialTemplateCommand apiFetchSocialTemplateCommand) throws ApiException {
        try {
            FetchSocialTemplateCommand fetchSocialTemplateCommand = apiFetchSocialTemplateCommand.toRequestCommand()
            SocialTemplateDto socialTemplateDto = templateService.fetchSocialTemplate(fetchSocialTemplateCommand)
            ApiSocialTemplateDto apiSocialTemplateDto = ApiSocialTemplateDto.createApiResponseDto(socialTemplateDto)
            return apiSocialTemplateDto
        } catch (BlankSocialAccountException blankSocialAccountException) {
            throw new ApiBlankSocialAccountException(blankSocialAccountException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiChangeEmailDto changeEmail(ApiChangeEmailCommand apiChangeEmailCommand) throws ApiException {
        try {
            ChangeEmailCommand changeEmailCommand = apiChangeEmailCommand.toRequestCommand()
            ChangeEmailDto changeEmailDto = userService.changeEmail(changeEmailCommand)
            ApiChangeEmailDto apiChangeEmailDto = ApiChangeEmailDto.createApiResponseDto(changeEmailDto)
            return apiChangeEmailDto
        } catch (BlankEmailException blankEmailException) {
            throw new ApiBlankEmailException(blankEmailException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (UniqueEmailException uniqueEmailException) {
            throw new ApiUniqueEmailException(uniqueEmailException)
        } catch (UnauthorizedEmailException unauthorizedEmailException) {
            throw new ApiUnauthorizedEmailException(unauthorizedEmailException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiCheckUserAccountDto checkUserAccount(ApiCheckUserAccountCommand apiCheckUserAccountCommand) {
        try {
            CheckUserAccountCommand checkUserAccountCommand = apiCheckUserAccountCommand.toRequestCommand()
            CheckUserAccountDto checkUserAccountDto = userService.checkUserAccount(checkUserAccountCommand)
            ApiCheckUserAccountDto apiCheckUserAccountDto = ApiCheckUserAccountDto.createApiResponseDto(checkUserAccountDto)
            return apiCheckUserAccountDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiVerifyUserEmailDto verifyEmail(ApiVerifyUserEmailCommand apiVerifyUserEmailCommand) {
        try {
            VerifyUserEmailCommand verifyUserEmailCommand = apiVerifyUserEmailCommand.toRequestCommand()
            VerifyUserEmailDto verifyUserEmailDto = userService.verifyEmail(verifyUserEmailCommand)
            ApiVerifyUserEmailDto apiVerifyUserEmailDto = ApiVerifyUserEmailDto.createApiResponseDto(verifyUserEmailDto)
            return apiVerifyUserEmailDto
        } catch (BlankVerificationTokenException blankVerificationTokenException) {
            throw new ApiBlankVerificationTokenException(blankVerificationTokenException)
        } catch (InvalidVerificationTokenException invalidVerificationTokenException) {
            throw new ApiInvalidVerificationTokenException(invalidVerificationTokenException)
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }

    ApiSendEmailVerificationCodeDto sendEmailVerificationCode(ApiSendEmailVerificationCodeCommand apiSendEmailVerificationCodeCommand) {
        try {
            SendEmailVerificationCodeCommand emailVerificationCodeCommand = apiSendEmailVerificationCodeCommand.toRequestCommand()
            SendEmailVerificationCodeDto sendEmailVerificationCodeDto = userService.sendEmailVerificationCode(emailVerificationCodeCommand)
            ApiSendEmailVerificationCodeDto apiSendEmailVerificationCodeDto = ApiSendEmailVerificationCodeDto.createApiResponseDto(sendEmailVerificationCodeDto)
            return apiSendEmailVerificationCodeDto
        } catch (UserNotFoundException userNotFoundException) {
            throw new ApiUserNotFoundException(userNotFoundException)
        } catch (CommonException commonException) {
            throw new ApiException(commonException)
        }
    }
}
