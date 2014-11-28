package com.famelive.common.streamManagement

import com.famelive.common.enums.streamManagement.StreamManagementConstantKeys
import com.famelive.common.util.streamManagement.StreamManagementUtil

/**
 * Created by anil on 13/11/14.
 */
class EventOfflineMediaContent extends EventMediaContent {

    static hasMany = [videoResolutions: Integer]

    void setDefaultValues() {
        isImageUrl = StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH_IS_URL)
        imagePath = StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_IMAGE_PATH)
        isVideoUrl = StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH_IS_URL)
        videoPath = StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_OFFLINE_VIDEO_PATH)
        videoExtension = StreamManagementUtil.fetchStreamManagementConstantDefaultValue(StreamManagementConstantKeys.EVENT_COMPLETE_VIDEO_EXTENSION)
        //TODO : fetch the available out stream resolutions from wowza server and set the appropriate values
    }

    static constraints = {
        imagePath nullable: true
        videoPath nullable: true
    }
}
