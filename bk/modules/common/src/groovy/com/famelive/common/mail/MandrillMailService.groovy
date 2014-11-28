package com.famelive.common.mail

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage

class MandrillMailService {

    /* Make an entry in resources.groovy, if want to use this service directly

    mailService(MandrillMailService) {
        grailsApp = ref('grailsApplication')
    }*/

    def grailsApp
    Properties mailProperties
    Session session

    void initializeMailConfig() {
        mailProperties = fetchMandrillMailConfig()
        session = fetchMandrillMailSession()
    }

    Properties fetchMandrillMailConfig() {
        Properties mailProperties = new Properties()
        mailProperties.setProperty("mail.transport.protocol", grailsApp.config.grails.mail.transport.protocol);
        mailProperties.setProperty("mail.smtp.host", grailsApp.config.grails.mail.host);
        mailProperties.setProperty("mail.smtp.port", String.valueOf(grailsApp.config.grails.mail.port));
        mailProperties.setProperty("mail.smtp.user", grailsApp.config.grails.mail.username);
        return mailProperties
    }

    Session fetchMandrillMailSession() {
        javax.mail.Authenticator authenticator = new MailAuthenticator(grailsApp.config.grails.mail.username, grailsApp.config.grails.mail.password);
        Properties properties = grailsApp.config.grails.mail.props
        return Session.getInstance(properties, authenticator);
    }

    MimeMessage buildMail(Session session, MailBuilder mailBuilder) {
        MimeMessage msg = new MimeMessage(session)
        if (mailBuilder.subject) {
            msg.setSubject(mailBuilder.subject, "UTF8")
        }
        if (mailBuilder.body) {
            msg.setText(mailBuilder.body, "UTF8")
        }
        if (mailBuilder.from) {
            msg.setFrom(mailBuilder.from)
        }
        if (mailBuilder.to) {
            msg.setRecipients(Message.RecipientType.TO, mailBuilder.to)
        }
        if (mailBuilder.cc) {
            msg.setRecipients(Message.RecipientType.CC, mailBuilder.cc)
        }
        if (mailBuilder.bcc) {
            msg.setRecipients(Message.RecipientType.BCC, mailBuilder.bcc)
        }
        return msg
    }

    boolean sendMail(MailBuilder mailBuilder) {
//        Properties mailProperties = fetchMandrillMailConfig()
//        final Session session = fetchMandrillMailSession();
        final MimeMessage msg = buildMail(session, mailBuilder)
        Transport.send(msg);
        return true
    }

    public void sendMail(Closure closure) {
        MailBuilder mailBuilder = new MailBuilder()
        closure.delegate = mailBuilder
        closure()
        sendMail(mailBuilder)
    }
}
