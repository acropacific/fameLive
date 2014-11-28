package com.famelive.common.slotManagement.rule

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.rule.CheckSlotAvailabilityRuleException
import com.famelive.common.exceptions.rule.RuleException
import com.famelive.common.slotManagement.ChannelSlot
import com.famelive.common.slotManagement.Slot
import com.famelive.common.slotmanagement.SlotService
import com.famelive.common.util.DateTimeUtil

class CheckSlotAvailabilityRule implements Rule {

    def grailsApplication
    Integer maxNumberOfChannels = 5
    SlotService slotService

    @Override
    void apply(RequestCommand requestCommand) throws RuleException {
        Date startTime = requestCommand?.startTime
        Integer duration = requestCommand?.duration
        Date endTime = DateTimeUtil.addMinutesToDate(startTime, duration)
        Slot slot = new Slot(startTime: startTime, endTime: endTime)
        List<ChannelSlot> channelSlots = slotService.fetchAvailableChannelSlots(slot)
        boolean ifSlotAvailableOnAnyChannel = (slotService.fetchRecommendedChannelSlot(channelSlots) == null) ? false : true
        if (!ifSlotAvailableOnAnyChannel) {
            throw new CheckSlotAvailabilityRuleException()
        }
    }
}
