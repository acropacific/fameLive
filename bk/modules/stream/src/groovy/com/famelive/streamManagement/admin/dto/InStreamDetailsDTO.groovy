package com.famelive.streamManagement.admin.dto

/**
 * Created by anil on 26/11/14.
 */
class InStreamDetailsDTO {
    String name
    long upTime
    long totalBytesIn
    long totalBytesOut
    long inByteRate
    long outByteRate
    long totalOutConnections
    String sourceIP
    Map<String, Integer> outStreamConnections
}
