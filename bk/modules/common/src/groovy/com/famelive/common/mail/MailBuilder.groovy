package com.famelive.common.mail

import javax.mail.internet.InternetAddress

class MailBuilder {
    InternetAddress to, from, cc, bcc
    String subject, body

    void to(String to) {
        this.to = new InternetAddress(to)
    }

    void from(String from) {
        this.from = new InternetAddress(from)
    }

    void cc(String cc) {
        this.cc = new InternetAddress(cc)
    }

    void bcc(String bcc) {
        this.bcc = new InternetAddress(bcc)
    }

    void subject(String subject) {
        this.subject = subject
    }

    void body(String body) {
        this.body = body
    }
}
