package com.oscaris.caterers.auth.services.impl;

import com.oscaris.caterers.auth.exceptions.errors.EmailSendFailedException;
import com.oscaris.caterers.auth.services.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Author: kev.Ameda
 */
@Service
public class MailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    public MailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true /* multipart */, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        }catch (EmailSendFailedException ex){
            throw new EmailSendFailedException(" Failed to send email to recipient "+ to, ex);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
