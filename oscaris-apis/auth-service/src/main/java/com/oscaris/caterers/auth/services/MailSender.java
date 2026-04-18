package com.oscaris.caterers.auth.services;

/**
  Author: kev.Ameda
 */

public interface MailSender {
    void sendEmail(String to, String subject, String body);
}
