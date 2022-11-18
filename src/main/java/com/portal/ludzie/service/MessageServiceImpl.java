package com.portal.ludzie.service;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MessageServiceImpl implements MessageService {
    private Session session;
    private String email = "";
    private String password = "";

    public MessageServiceImpl() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        try {
            session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });
        } catch (Exception ex) {
        }

    }

    @Override
    public void sendEmail(String senderEmail, String receiverEmail, String emailTitle, String emailContent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );
            message.setSubject(emailTitle);
            String emailContentPrefix = "Wiadomość od: " + senderEmail + "\n";
            message.setText(emailContentPrefix + emailContent);

            Transport.send(message);

        } catch (Exception ex) {
            System.out.println("Błąd wysyłki maila " + ex.getMessage());
        }
    }
}
