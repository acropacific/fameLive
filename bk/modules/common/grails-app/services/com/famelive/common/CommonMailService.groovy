package com.famelive.common

import com.famelive.common.dto.usermanagement.EMailDto
import com.famelive.common.mail.MandrillMailTemplateHeaders
import com.famelive.common.user.User
import grails.plugin.asyncmail.AsynchronousMailService

class CommonMailService {

    AsynchronousMailService asynchronousMailService
    def grailsApplication

    void sendMail(EMailDto eMailDto, Map header) {
        if (eMailDto?.to) {
            asynchronousMailService.sendMail {
                from "admin@famelive.com"
                to eMailDto.to.toArray()
                subject eMailDto.subject
                html eMailDto.html
                headers header
            }
        }
    }

    void sendMail(EMailDto eMailDto) {
        if (eMailDto?.to) {
            asynchronousMailService.sendMail {
                from "admin@famelive.com"
                to eMailDto.to.toArray()
                subject eMailDto.subject
                html eMailDto.html
            }
        }
    }

    void sendForgotPasswordMail(User user) {
        Map header = [:]
        header.put(MandrillMailTemplateHeaders.X_MC_Template.value, grailsApplication.config.famelive.forgotPassword.mail.template)
        header.put(MandrillMailTemplateHeaders.X_MC_MergeVars.value, '{"VERIFICATION_CODE":"' + user?.forgotPasswordCode + '"}')
        EMailDto eMailDto = new EMailDto(to: [user?.email], subject: "ForgotPassword", html: "Please Use below code to login \n ${user?.forgotPasswordCode}")
        sendMail(eMailDto, header)
    }

    void sendRegistrationMailToPerformer(User user) {
        Map header = [:]
        header.put(MandrillMailTemplateHeaders.X_MC_Template.value, grailsApplication.config.famelive.performer.registration.mail.template)
        header.put(MandrillMailTemplateHeaders.X_MC_MergeVars.value, '{"USER_NAME":"' + user?.username + '"}')
        EMailDto eMailDto = new EMailDto(to: [user?.email], subject: "Registration Successfully", html: "Registration Successfully")
        sendMail(eMailDto, header)
    }

    void sendRegistrationMailToViewer(User user) {
        Map header = [:]
        header.put(MandrillMailTemplateHeaders.X_MC_Template.value, grailsApplication.config.famelive.viewer.registration.mail.template)
        header.put(MandrillMailTemplateHeaders.X_MC_MergeVars.value, '{"USER_NAME":"' + user?.username + '"}')
        EMailDto eMailDto = new EMailDto(to: [user?.email], subject: "Registration Successfully", html: "Registration Successfully")
        sendMail(eMailDto, header)
    }

    void sendCreateEventMail(User user) {
        Map header = [:]
        header.put(MandrillMailTemplateHeaders.X_MC_Template.value, grailsApplication.config.famelive.createEvent.mail.template)
        header.put(MandrillMailTemplateHeaders.X_MC_MergeVars.value, '{"USER_NAME":"' + user?.username + '"}')
        EMailDto eMailDto = new EMailDto(to: [user?.email], subject: "Create Event Successfully", html: "Create Event Successfully")
        sendMail(eMailDto, header)
    }

    void sendCancelEventMail(User user) {
        Map header = [:]
        header.put(MandrillMailTemplateHeaders.X_MC_Template.value, grailsApplication.config.famelive.cancelEvent.mail.template)
        header.put(MandrillMailTemplateHeaders.X_MC_MergeVars.value, '{"USER_NAME":"' + user?.username + '"}')
        EMailDto eMailDto = new EMailDto(to: [user?.email], subject: "Cancel Event Successfully", html: "Cancel Event Successfully")
        sendMail(eMailDto, header)
    }
}
