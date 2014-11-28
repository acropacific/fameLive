package com.famelive.common.util

import com.famelive.common.mail.MailBuilder

import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * Created by anil on 23/9/14.
 */
class SendMandrillMail {
    static MailBuilder mailBuilder = new MailBuilder()
    static String host = "smtp.mandrillapp.com";
    static InternetAddress from = new InternetAddress("admn.famelive@gmail.com")
    static InternetAddress to = new InternetAddress("anil.agrawal@intelligrape.com")
    static InternetAddress cc = new InternetAddress("anil.agrawal@intelligrape.com")
    static InternetAddress bcc = new InternetAddress("anil.agrawal@intelligrape.com")
    static String subject = "sub"
    static String body = "bo"
    static String username = "admn.famelive@gmail.com"
    static String password = "7dqZF1M5VEsbBcyEgikZ9A"
    static int port = 587
    static String protocol = "smtp"

    static boolean sendMail() {
        Properties mailProperties = new Properties()
        mailProperties.setProperty("mail.transport.protocol", protocol);
        mailProperties.setProperty("mail.smtp.host", host);
        mailProperties.setProperty("mail.smtp.port", String.valueOf(port));
        mailProperties.setProperty("mail.smtp.user", username);
        final Session session = getSession();
        /*final Session session = Session.getInstance(mailProperties, null);
        session.setPasswordAuthentication(
                new URLName(protocol, host, -1, null, username, null),
                new PasswordAuthentication(username, password));*/

        final MimeMessage msg = new MimeMessage(session);
        msg.setSubject("subject", "UTF8");
        msg.setText("txt", "UTF8")
        msg.setFrom(from)
        msg.setRecipients(Message.RecipientType.TO, to)
// set required message properties

        Transport.send(msg);
        return true
    }

    private static Session getSession() {
        Authenticator authenticator = new Authenticator();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
        properties.setProperty("mail.smtp.auth", "true");

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", String.valueOf(port));

        return Session.getInstance(properties, authenticator);
    }

    private static class Authenticator extends javax.mail.Authenticator {
        private PasswordAuthentication authentication;

        public Authenticator() {
            String username = username;
            String password = password;
            authentication = new PasswordAuthentication(username, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }

    }

    private static setMailProperties() {
        if (mailBuilder) {
            to = mailBuilder.to
            from = mailBuilder.from
            cc = mailBuilder.cc
            bcc = mailBuilder.bcc
            subject = mailBuilder.subject
            body = mailBuilder.body
        }
    }


    public static void sendMail(Closure closure) {
        closure.delegate = mailBuilder
        closure()
        setMailProperties()
        sendMail()
    }
}