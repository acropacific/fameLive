package com.famelive.common.slotmanagement

import com.famelive.common.callBroker.RequestCallBrokerAdapter
import com.famelive.common.enums.slotmanagement.SlotManagementConstantKeys
import com.famelive.common.slotManagement.ChannelSlot
import com.famelive.common.slotManagement.Slot
import com.famelive.common.streamManagement.WowzaChannel
import com.famelive.common.util.slotmanagement.SlotManagementUtil

class SlotService {
    RequestCallBrokerAdapter requestCallBrokerAdapter

    List<ChannelSlot> fetchAvailableChannelSlots(Slot slot) {
        List<ChannelSlot> channelSlots = []
        List<WowzaChannel> channels = WowzaChannel.findAllByIsVODChannelAndIsActive(false, true)
        channels.each { channel ->
            ChannelSlot channelSlot = fetchAvailableChannelSlotForChannel(new ChannelSlot(startTime: slot.startTime, endTime: slot.endTime, channel: channel))
            if (channelSlot) {
                channelSlots.add(channelSlot)
            }
        }
        return channelSlots
    }

    /* ******************** handled scenarios When reserved slots timings removed from available slots **********************
    boolean isChannelSlotComesUnderReservedSlots(ChannelSlot channelSlot) {
        WowzaChannel channel = channelSlot.channel
        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        boolean isFirstRestTimePresent = false
        Date firstRestTimeStartTime
        Date firstRestTimeEndTime
        boolean isLastRestTimePresent = false
        Date lastRestTimeStartTime
        Date lastRestTimeEndTime
        boolean isComesUnderReservedSlots = false
        List<ReservedChannelSlot> reservedChannelSlots = ReservedChannelSlot.findAllByChannel(channel)
        reservedChannelSlots.each { reservedChannelSlot ->
            boolean ifNeedToCheckRestTime = false
            boolean isCaseOneOccurred = false
            boolean isCaseTwoOccurred = false
            boolean isCaseOneComesUnderReservedSlots = false
            boolean isCaseTwoComesUnderReservedSlots = false

            //Case 1 (reservedSlotStartTime comes during ChannelSlot)
            if (reservedChannelSlot.startTime >= startTime && reservedChannelSlot.startTime < endTime) {
                if (reservedChannelSlot.startTime.compareTo(startTime) > 0) {
                    isFirstRestTimePresent = true
                    firstRestTimeStartTime = startTime
                    firstRestTimeEndTime = reservedChannelSlot.startTime
                    ifNeedToCheckRestTime = true
                } else {
                    isCaseOneComesUnderReservedSlots = true
                }
                isCaseOneOccurred = true
            }

            //Case 2 (reservedSlotEndTime comes during ChannelSlot)
            if (reservedChannelSlot.endTime > startTime && reservedChannelSlot.endTime <= endTime) {
                if (reservedChannelSlot.endTime.compareTo(endTime) < 0) {
                    isLastRestTimePresent = true
                    lastRestTimeStartTime = reservedChannelSlot.endTime
                    lastRestTimeEndTime = endTime
                    ifNeedToCheckRestTime = true
                } else {
                    isCaseTwoComesUnderReservedSlots = true
                }
                isCaseTwoOccurred = true
            }

            //Case 3 (ChannelSlot comes during reservedSlot)
            if (reservedChannelSlot.startTime <= startTime && reservedChannelSlot.endTime >= endTime) {
                isComesUnderReservedSlots = true
            }

            if (isCaseOneOccurred && isCaseTwoOccurred) {
                if (isCaseOneComesUnderReservedSlots && isCaseTwoComesUnderReservedSlots) {
                    isComesUnderReservedSlots = true
                }
            } else if (isCaseOneOccurred) {
                if (isCaseOneComesUnderReservedSlots) {
                    isComesUnderReservedSlots = true
                }
            } else if (isCaseTwoOccurred) {
                if (isCaseTwoComesUnderReservedSlots) {
                    isComesUnderReservedSlots = true
                }
            }

            if (!ifNeedToCheckRestTime) {
                //Do nothing value for isComesUnderReservedSlots is already set
            } else if (isFirstRestTimePresent && isLastRestTimePresent) {
                if (isChannelSlotComesUnderAvailableSlots(new ChannelSlot(startTime: firstRestTimeStartTime, endTime: firstRestTimeEndTime, channel: channel))
                        && isChannelSlotComesUnderAvailableSlots(new ChannelSlot(startTime: lastRestTimeStartTime, endTime: lastRestTimeEndTime, channel: channel))) {
                    isComesUnderReservedSlots = true
                }
            } else if (isFirstRestTimePresent) {
                if (isChannelSlotComesUnderAvailableSlots(new ChannelSlot(startTime: firstRestTimeStartTime, endTime: firstRestTimeEndTime, channel: channel))) {
                    isComesUnderReservedSlots = true
                }
            } else if (isLastRestTimePresent) {
                if (isChannelSlotComesUnderAvailableSlots(new ChannelSlot(startTime: lastRestTimeStartTime, endTime: lastRestTimeEndTime, channel: channel))) {
                    isComesUnderReservedSlots = true
                }
            }
        }
        return isComesUnderReservedSlots
    }

    ChannelSlot fetchAvailableChannelSlotForChannel(ChannelSlot channelSlot) {
        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        WowzaChannel channel = channelSlot.channel
        if (isChannelSlotComesUnderReservedSlots(ChannelSlot)) {
            channelSlot.isReserved = true
        } else if (isComesUnderAvailableSlots) {
            channelSlot.isAvailable = true
        } else {
            channelSlot.isBooked = true
        }
        return channelSlot
    }
    */

    boolean isChannelSlotComesUnderReservedSlots(ChannelSlot channelSlot) {
        WowzaChannel channel = channelSlot.channel
        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        boolean isComesUnderReservedSlots = false
        List<ReservedChannelSlot> reservedChannelSlots = ReservedChannelSlot.findAllByChannel(channel)
        reservedChannelSlots.each { reservedChannelSlot ->

            //Case 1 (reservedSlotStartTime comes during ChannelSlot)
            if (reservedChannelSlot.startTime >= startTime && reservedChannelSlot.startTime < endTime) {
                isComesUnderReservedSlots = true
            }

            //Case 2 (reservedSlotEndTime comes during ChannelSlot)
            if (reservedChannelSlot.endTime > startTime && reservedChannelSlot.endTime <= endTime) {
                isComesUnderReservedSlots = true
            }

            //Case 3 (ChannelSlot comes during reservedSlot)
            if (reservedChannelSlot.startTime <= startTime && reservedChannelSlot.endTime >= endTime) {
                isComesUnderReservedSlots = true
            }
        }
        return isComesUnderReservedSlots
    }

    boolean isChannelSlotComesUnderAvailableSlots(ChannelSlot channelSlot) {
        WowzaChannel channel = channelSlot.channel
        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        boolean isComesUnderAvailableSlots = false
        List<AvailableChannelSlot> availableChannelSlots = AvailableChannelSlot.findAllByChannel(channel)
        availableChannelSlots.each { availableChannelSlot ->
            if (availableChannelSlot.startTime <= startTime && availableChannelSlot.endTime >= endTime) {
                isComesUnderAvailableSlots = true
            }
        }
        return isComesUnderAvailableSlots
    }

    ChannelSlot fetchAvailableChannelSlotForChannel(ChannelSlot channelSlot) {
        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        WowzaChannel channel = channelSlot.channel

        boolean isComesUnderAvailableSlots = isChannelSlotComesUnderAvailableSlots(channelSlot)

        if (isComesUnderAvailableSlots && isChannelSlotComesUnderReservedSlots(channelSlot)) {
            channelSlot.isReserved = true
        } else if (isComesUnderAvailableSlots) {
            channelSlot.isAvailable = true
        } else {
            channelSlot.isBooked = true
        }
        return channelSlot
    }

    BookedChannelSlot bookChannelSlot(ChannelSlot channelSlot) {
        BookedChannelSlot bookedChannelSlot

        Date startTime = channelSlot.startTime
        Date endTime = channelSlot.endTime
        WowzaChannel channel = channelSlot.channel

        //Re-verifying if channelSlot is available to book
        boolean isChannelSlotComesUnderAvailableSlots = isChannelSlotComesUnderAvailableSlots(channelSlot)
        if (isChannelSlotComesUnderAvailableSlots) {
            //Find the record from AvailableChannelSlot where channelSlot fits
            List<AvailableChannelSlot> availableChannelSlots = AvailableChannelSlot.findAllByChannel(channel)
            AvailableChannelSlot availableChannelSlot = availableChannelSlots.find { availableSlot ->
                if (availableSlot.startTime <= startTime && availableSlot.endTime >= endTime) {
                    return true
                }
            }
            AvailableChannelSlot newAvailableChannelSlotFirstPart
            AvailableChannelSlot newAvailableChannelSlotLastPart
            if (availableChannelSlot.startTime.compareTo(startTime) < 0) {
                newAvailableChannelSlotFirstPart = new AvailableChannelSlot(startTime: availableChannelSlot.startTime, endTime: startTime, channel: channel)
            }
            if (availableChannelSlot.endTime.compareTo(endTime) > 0) {
                newAvailableChannelSlotLastPart = new AvailableChannelSlot(startTime: endTime, endTime: availableChannelSlot.endTime, channel: channel)
            }
            AvailableChannelSlot.withTransaction {
                availableChannelSlot.delete(flush: true)
                newAvailableChannelSlotFirstPart.save(failOnError: true, flush: true)
                newAvailableChannelSlotLastPart.save(failOnError: true, flush: true)
            }
            bookedChannelSlot = new BookedChannelSlot(startTime: startTime, endTime: endTime, channel: channel)
            bookedChannelSlot = bookedChannelSlot.save(failOnError: true, flush: true)
        }
        return bookedChannelSlot
    }

    ChannelSlot fetchRecommendedChannelSlot(List<ChannelSlot> channelSlots) {
        List<ChannelSlot> availableChannelSlots = channelSlots.findAll { channelSlot ->
            if (channelSlot.isAvailable || (SlotManagementUtil.fetchSlotManagementConstantForUser(SlotManagementConstantKeys.IS_USER_ALLOWED_FOR_RESERVED_SLOTS) && channelSlot.isReserved)) {
                return true
            }
        }
        return availableChannelSlots?.get(0)
    }

    boolean cancelBookedEventChannelSlot(Event event) {
        BookedChannelSlot bookedChannelSlot = event.bookedChannelSlot
        Date startTime = bookedChannelSlot.startTime
        Date endTime = bookedChannelSlot.endTime
        WowzaChannel channel = bookedChannelSlot.channel
        event.bookedChannelSlot = null
        event.save(flush: true).refresh()
        bookedChannelSlot.delete(flush: true)
        AvailableChannelSlot startTimeMatchedSlot
        AvailableChannelSlot endTimeMatchedSlot

        List<AvailableChannelSlot> availableChannelSlots = AvailableChannelSlot.findAllByChannel(channel)
        availableChannelSlots.each { availableChannelSlot ->
            if (availableChannelSlot.endTime.compareTo(startTime) == 0) {
                startTimeMatchedSlot = availableChannelSlot
            }
            if (availableChannelSlot.startTime.compareTo(endTime) == 0) {
                endTimeMatchedSlot = availableChannelSlot
            }
        }
        if (startTimeMatchedSlot != null && endTimeMatchedSlot != null && startTimeMatchedSlot == endTimeMatchedSlot) {
            AvailableChannelSlot.withTransaction {
                endTimeMatchedSlot.delete(flush: true)
                startTimeMatchedSlot.setEndTime(endTimeMatchedSlot.endTime)
                startTimeMatchedSlot.save(failOnError: true, flush: true)
            }
        } else if (startTimeMatchedSlot != null && endTimeMatchedSlot != null) {
            //This case should never occur, as we are doing operation on a single channel
        } else if (startTimeMatchedSlot != null) {
            AvailableChannelSlot.withTransaction {
                startTimeMatchedSlot.setEndTime(endTime)
                startTimeMatchedSlot.save(failOnError: true, flush: true)
            }

        } else if (endTimeMatchedSlot != null) {
            AvailableChannelSlot.withTransaction {
                endTimeMatchedSlot.setStartTime(startTime)
                endTimeMatchedSlot.save(failOnError: true, flush: true)
            }
        } else {
            new AvailableChannelSlot(startTime: startTime, endTime: endTime, channel: channel).save(failOnError: true, flush: true)
        }
        BookedChannelSlotHistory bookedChannelSlotHistory = new BookedChannelSlotHistory(bookedChannelSlot.properties)
        bookedChannelSlotHistory.setEvent(event)
        bookedChannelSlotHistory.save()
        return true
    }
}
