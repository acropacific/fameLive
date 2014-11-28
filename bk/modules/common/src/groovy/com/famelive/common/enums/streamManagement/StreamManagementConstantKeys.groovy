package com.famelive.common.enums.streamManagement

/**
 * Created by anil on 5/11/14.
 */
public enum StreamManagementConstantKeys {

    EVENT_UPCOMING_IMAGE_PATH("eventUpcomingImagePath", String.class),
    EVENT_UPCOMING_VIDEO_PATH("eventUpcomingVideoPath", String.class),
    EVENT_READY_IMAGE_PATH("eventReadyImagePath", String.class),
    EVENT_READY_VIDEO_PATH("eventReadyVideoPath", String.class),
    EVENT_OFFLINE_IMAGE_PATH("eventOfflineImagePath", String.class),
    EVENT_OFFLINE_VIDEO_PATH("eventOfflineVideoPath", String.class),
    EVENT_COMPLETE_IMAGE_PATH("eventCompleteImagePath", String.class),
    EVENT_COMPLETE_VIDEO_PATH("eventCompleteVideoPath", String.class),

    EVENT_UPCOMING_IMAGE_PATH_IS_URL("eventUpcomingImagePathIsUrl", Boolean.class),
    EVENT_UPCOMING_VIDEO_PATH_IS_URL("eventUpcomingVideoPathIsUrl", Boolean.class),
    EVENT_READY_IMAGE_PATH_IS_URL("eventReadyImagePathIsUrl", Boolean.class),
    EVENT_READY_VIDEO_PATH_IS_URL("eventReadyVideoPathIsUrl", Boolean.class),
    EVENT_OFFLINE_IMAGE_PATH_IS_URL("eventOfflineImagePathIsUrl", Boolean.class),
    EVENT_OFFLINE_VIDEO_PATH_IS_URL("eventOfflineVideoPathIsUrl", Boolean.class),
    EVENT_COMPLETE_IMAGE_PATH_IS_URL("eventCompleteImagePathIsUrl", Boolean.class),
    EVENT_COMPLETE_VIDEO_PATH_IS_URL("eventCompleteVideoPathIsUrl", Boolean.class),

    EVENT_UPCOMING_VIDEO_EXTENSION("eventUpcomingVideoExtension", String.class),
    EVENT_READY_VIDEO_EXTENSION("eventReadyVideoExtension", String.class),
    EVENT_OFFLINE_VIDEO_EXTENSION("eventOfflineVideoExtension", String.class),
    EVENT_COMPLETE_VIDEO_EXTENSION("eventCompleteVideoExtension", String.class),
    DEFAULT_STREAM_FOR_FILLER_VIDEO("defaultStreamForFillerVideo", String.class),
    DEFAULT_STREAMING_PROTOCOL_FOR_WEB("defaultStreamingProtocolForWeb", String.class),
    IS_DVR_ENABLED_FOR_LIVE_STREAMS("isDVREnabledForLiveStreams", Boolean.class)

    String key;
    Class valueType;

    StreamManagementConstantKeys(String key, Class valueType) {
        this.key = key;
        this.valueType = valueType;
    }
}
