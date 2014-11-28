package com.famelive.common.enums.streamManagement

/**
 * Created by anil on 25/11/14.
 */
enum StreamingProtocols {

    RTSP("rtsp"),
    RTMP("rtmp"),
    HLS("hls")

    String value

    StreamingProtocols(String value) {
        this.value = value
    }
}
