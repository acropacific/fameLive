package com.famelive.common.slotManagement.rule

import com.famelive.common.command.RequestCommand

interface Rule {

    void apply(RequestCommand requestCommand)
}