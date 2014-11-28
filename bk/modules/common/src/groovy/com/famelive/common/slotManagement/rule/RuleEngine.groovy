package com.famelive.common.slotManagement.rule

import com.famelive.common.command.RequestCommand
import com.famelive.common.exceptions.rule.RuleException

class RuleEngine {

    def grailsApplication

    List<Rule> rules

    void applyRules(RequestCommand requestCommand) throws RuleException {
        rules.each { Rule rule ->
            rule.apply(requestCommand)
        }
    }
}