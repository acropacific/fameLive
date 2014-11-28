package com.famelive.api.enums

public enum APIErrorCode {
    ApiException(100),
    ApiInvalidTokenException(101),
    ApiPasswordNotEqualException(102),
    ApiValidationException(103),
    ApiEmailNotFoundException(104),
    ApiBlankEmailException(105),
    ApiBlankForgotPasswordCodeException(106),
    ApiBlankPasswordException(107),
    ApiInvalidForgotPasswordCodeException(108),
    ApiUnauthorizedEmailException(109),
    ApiBlankUserNameException(110),
    ApiUserNameMaxSizeException(111),
    ApiPasswordMinLengthException(112),
    ApiUniqueEmailException(113),
    ApiBlankMobileException(114),
    ApiMobileNumberLengthException(115),
    ApiMobileNumberInvalidException(116),
    ApiBlankFameNameException(117),
    ApiFameNameMaxLengthException(118),
    ApiUniqueFameNameException(119),
    ApiUserNotFoundException(120),
    ApiBlankNewPasswordException(121),
    ApiInvalidLoginCredentialException(122),
    ApiNewPasswordConfirmPasswordNotSameException(123),
    ApiBlankUserTypeException(124),
    ApiBlankUserRegistrationTypeException(125),
    ApiUserAccountBlockException(126),
    ApiBlankSocialAccountException(127),
    ApiInvalidUserSocialAccountException(128),
    ApiPasswordPatternException(129),

    ApiBlankEventNameException(130),     //    Slot Management
    ApiEventNameMaxLengthException(131),
    ApiEventDescriptionMaxLengthException(132),
    ApiBlankEventStartTimeException(133),
    ApiBlankEventGenreException(134),
    ApiBlankEventDurationException(135),
    ApiEventNotFoundException(136),
    ApiBlankEventIdException(137),
    ApiBlankEventImageNameException(138),
    ApiEventCancelTimeInvalidException(139),
    ApiCheckSlotAvailabilityRuleException(140),
    ApiBlankEventStatusException(141),
    ApiCheckPublishedGenreRuleException(142),
    ApiCheckConflictUserEventRuleException(143),
    ApiEventStreamInfoNotFoundException(144),

    ApiUserInvalidOperationException(180), //User
    ApiPerformerNotFoundException(181), //User
    ApiBlankFameIdException(182), //User


    ApiInvalidPerformerIdException(200), //Follow Mangement
    ApiBlankPerformerIdException(201),
    ApiInvalidFollowDetailException(202),
    ApiInvalidPerformerException(203)



    int errorCode;
    String errorMessage;

    APIErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}