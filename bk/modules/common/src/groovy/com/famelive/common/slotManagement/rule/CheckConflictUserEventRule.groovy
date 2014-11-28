package com.famelive.common.slotManagement.rule

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.rule.CheckConflictUserEventRuleException
import com.famelive.common.exceptions.rule.RuleException
import com.famelive.common.slotmanagement.Event
import com.famelive.common.user.User
import com.famelive.common.util.DateTimeUtil

class CheckConflictUserEventRule implements Rule {

    Integer maxCount = 0

    @Override
    void apply(RequestCommand requestCommand) throws RuleException {
        Long id = requestCommand?.id
        Date startTime = requestCommand?.startTime
        Integer duration = requestCommand?.duration
        Date endTime = DateTimeUtil.addMinutesToDate(startTime, duration)
        User user = User.get(id)
        Integer count = Event.getTotalEventsBetweenDuration(startTime, endTime, user)
        //Same user can't own more than one event at same time
        if (count > maxCount) {
            throw new CheckConflictUserEventRuleException()
        }
    }
}
