package com.famelive.api.dto.configuration

import com.famelive.api.annotation.APIResponseClass
import com.famelive.api.annotation.APIResponseField
import com.famelive.api.constant.ApiConstants
import com.famelive.api.dto.ApiResponseDto
import com.famelive.api.enums.APIActions
import com.famelive.api.enums.ApiEventStatus
import com.famelive.api.util.ApiMessagesUtil
import com.famelive.common.dto.usermanagement.ConfigurationDto

@APIResponseClass
class ApiConfigurationDto extends ApiResponseDto {

    //User Management
    @APIResponseField(include = true, parentName = "constraints")
    public Integer usernameMaxSize
    @APIResponseField(include = true, parentName = "constraints")
    public Integer fameNameMaxSize
    @APIResponseField(include = true, parentName = "constraints")
    public Integer mobileNumberSize
    @APIResponseField(include = true, parentName = "constraints")
    public String mobileNumberPattern
    @APIResponseField(include = true, parentName = "constraints")
    public Integer passwordMinSize
    @APIResponseField(include = true, parentName = "constraints")
    public String emailPattern
    @APIResponseField(include = true, parentName = "constraints")
    public String passwordPattern

    @APIResponseField(include = true, parentName = "medium")
    public String facebook
    @APIResponseField(include = true, parentName = "medium")
    public String gPlus
    @APIResponseField(include = true, parentName = "medium")
    public String manual

    @APIResponseField(include = true, parentName = "userType")
    public String performer
    @APIResponseField(include = true, parentName = "userType")
    public String viewer

    //Event Status
    @APIResponseField(include = true, parentName = "eventStatus")
    public String cancelledEvent
    @APIResponseField(include = true, parentName = "eventStatus")
    public String onAirEvent
    @APIResponseField(include = true, parentName = "eventStatus")
    public String completedEvent
    @APIResponseField(include = true, parentName = "eventStatus")
    public String upComingEvent

    //Slot Management
    @APIResponseField(include = true, parentName = "constraints")
    public Integer eventNameMaxSize
    @APIResponseField(include = true, parentName = "constraints")
    public Integer eventDescriptionMaxSize
    @APIResponseField(include = true, parentName = "constraints")
    public String eventSlotDuration

    //Cloudinary
    @APIResponseField(include = true, parentName = "cloudinary")
    public String cloudName
    @APIResponseField(include = true, parentName = "cloudinary")
    public String apiKey
    @APIResponseField(include = true, parentName = "cloudinary")
    public String apiSecret
    @APIResponseField(include = true, parentName = "cloudinary")
    public String folder
    @APIResponseField(include = true, parentName = "cloudinary")
    public String mimeType
    @APIResponseField(include = true, parentName = "cloudinary")
    public String baseUrl
    @APIResponseField(include = true, parentName = "cloudinary")
    public String userImageFolder
    @APIResponseField(include = true, parentName = "cloudinary")
    public String userImageSize
    @APIResponseField(include = true, parentName = "cloudinary")
    public String eventImageFolder
    @APIResponseField(include = true, parentName = "cloudinary")
    public String eventPosterSize

    //Other
    @APIResponseField(include = true, parentName = "date")
    public String sendingDateFormat
    @APIResponseField(include = true, parentName = "date")
    public String showingDateFormat

    @APIResponseField(include = true)
    public def messages

    ApiConfigurationDto() {}

    ApiConfigurationDto(ConfigurationDto configurationDto) {
        //User Management
        this.usernameMaxSize = configurationDto.usernameMaxSize
        this.fameNameMaxSize = configurationDto.fameNameMaxSize
        this.mobileNumberPattern = configurationDto.mobileNumberPattern
        this.emailPattern = configurationDto.emailPattern
        this.passwordPattern = configurationDto.passwordPattern
        this.mobileNumberSize = configurationDto.mobileNumberSize
        this.passwordMinSize = configurationDto.passwordMinSize
        this.facebook = configurationDto?.facebook?.toString()
        this.gPlus = configurationDto?.gPlus?.toString()
        this.manual = configurationDto?.manual?.toString()
        this.performer = configurationDto?.performer?.toString()
        this.viewer = configurationDto?.viewer?.toString()
        //Slot Management
        this.eventNameMaxSize = configurationDto.eventNameMaxSize
        this.eventDescriptionMaxSize = configurationDto.eventDescriptionMaxSize
        this.eventSlotDuration = configurationDto.eventSlotDuration
        this.cancelledEvent = ApiEventStatus.CANCELED.toString()
        this.onAirEvent = ApiEventStatus.ON_AIR.toString()
        this.completedEvent = ApiEventStatus.COMPLETED.toString()
        this.upComingEvent = ApiEventStatus.UP_COMING.toString()
        //Cloudinary
        this.cloudName = configurationDto.cloudName
        this.apiKey = configurationDto.apiKey
        this.apiSecret = configurationDto.apiSecret
        this.folder = configurationDto.folder
        this.mimeType = configurationDto.mimeType
        this.baseUrl = configurationDto.baseUrl
        this.userImageFolder = configurationDto.userImageFolder
        this.userImageSize = configurationDto.userImageSize
        this.eventImageFolder = configurationDto.eventImageFolder
        this.eventPosterSize = configurationDto.eventPosterSize
        //Others
        this.sendingDateFormat = configurationDto.dateFormat
        this.showingDateFormat = ApiConstants.DATE_TIME_FORMAT

        this.status = ApiConstants.MOBILE_API_SUCCESS_CODE
        this.code = APIActions.CONFIGURATION.successCode
        this.message = ApiMessagesUtil.messageSource.getProperty("${this.code}")
    }

    static ApiConfigurationDto createApiResponseDto(ConfigurationDto configurationDto) {
        return new ApiConfigurationDto(configurationDto)
    }

}
