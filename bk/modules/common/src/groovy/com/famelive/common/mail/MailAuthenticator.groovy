package com.famelive.common.mail

class MailAuthenticator extends javax.mail.Authenticator {
    private javax.mail.PasswordAuthentication authentication;

    public MailAuthenticator(String username, String password) {
        authentication = new javax.mail.PasswordAuthentication(username, password);
    }

    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }

}