package com.famelive.api.exceptions.rule

class ApiCheckSlotAvailabilityRuleException extends ApiRuleException {

    ApiCheckSlotAvailabilityRuleException() {}

    ApiCheckSlotAvailabilityRuleException(String message) {
        super(message)
    }

    ApiCheckSlotAvailabilityRuleException(String message, Throwable cause) {
        super(message, cause)
    }

    ApiCheckSlotAvailabilityRuleException(Throwable cause) {
        super(cause)
    }

}
