package com.famelive.common.dto.usermanagement

import com.famelive.common.constant.CommonConstants
import com.famelive.common.constant.Constraints
import com.famelive.common.dto.ResponseDto
import com.famelive.common.enums.usermanagement.UserRegistrationType

class ConfigurationDto extends ResponseDto {

    //User Management
    Integer usernameMaxSize
    Integer fameNameMaxSize
    Integer mobileNumberSize
    String mobileNumberPattern
    String emailPattern
    String passwordPattern
    Integer passwordMinSize

    //User type
    UserRegistrationType facebook
    UserRegistrationType gPlus
    UserRegistrationType manual

    //Cloudinary
    String cloudName
    String apiKey
    String apiSecret
    String folder
    String mimeType
    String baseUrl
    String userImageFolder
    String userImageSize
    String eventImageFolder
    String eventPosterSize

    //Slot Management
    Integer eventNameMaxSize
    Integer eventDescriptionMaxSize
    String eventSlotDuration

    //Other
    String dateFormat

    ConfigurationDto() {}

    ConfigurationDto(Map configMap) {
        this.usernameMaxSize = Constraints.USERNAME_MAX_SIZE
        this.fameNameMaxSize = Constraints.FAMENAME_MAX_SIZE
        this.mobileNumberSize = Constraints.MOBILE_NUMBER_SIZE
        this.mobileNumberPattern = Constraints.MOBILE_NUMBER_PATTERN
        this.emailPattern = Constraints.EMAIL_PATTERN
        this.passwordPattern = Constraints.PASSWORD_PATTERN
        this.passwordMinSize = Constraints.PASSWORD_MIN_SIZE
        this.facebook = UserRegistrationType.FACEBOOK
        this.gPlus = UserRegistrationType.G_PLUS
        this.manual = UserRegistrationType.MANUAL
        this.eventNameMaxSize = Constraints.EVENT_NAME_MAX_SIZE
        this.eventDescriptionMaxSize = Constraints.EVENT_DESCRIPTION_MAX_SIZE
        this.eventSlotDuration = CommonConstants.EVENT_SLOT_DURATIONS.toString()
        this.dateFormat = CommonConstants.INPUT_DATE_FORMAT
        this.cloudName = configMap.cloud_name
        this.apiKey = configMap.api_key
        this.apiSecret = configMap.api_secret
        this.folder = configMap.folder
        this.mimeType = configMap.mimeType
        this.baseUrl = configMap.baseUrl
        this.baseUrl = configMap.baseUrl
        this.userImageFolder = configMap.userImageFolder
        this.userImageSize = configMap.userImageSize
        this.eventImageFolder = configMap.eventImageFolder
        this.eventPosterSize = configMap.eventPosterSize
    }

    static ConfigurationDto createCommonResponseDto(Map map) {
        return new ConfigurationDto(map)
    }

}
