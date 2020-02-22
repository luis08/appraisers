package com.appraisers.app.gmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class GmailService {

    @Qualifier("getJavaMailSender")
    @Autowired
    public JavaMailSender emailSender;

    public void sendMessage(String[] to, String subject, String text, String[] cc, String[] bcc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if(cc != null) {
            message.setCc(cc);
        }

        if(bcc != null) {
            message.setBcc(bcc);
        }

        emailSender.send(message);
    }

}
