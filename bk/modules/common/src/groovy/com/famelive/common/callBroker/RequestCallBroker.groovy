package com.famelive.common.callBroker

import com.famelive.streamManagement.command.RequestCO

/**
 * Created by anil on 17/10/14.
 */
interface RequestCallBroker {
    RequestCallBrokerResponse execute(RequestCO requestCO);
}
