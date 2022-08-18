package com.example.demo.repository;

import com.example.demo.dal.EmailDAL;
import com.example.demo.util.Constants;
import org.springframework.stereotype.Repository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;


@Repository
public class SendEmailRepository implements EmailDAL {

    @Override
    public String sendEmail() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("divyanshusonwani@gmail.com", "kzapfwxnttbljtol");
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("noreply@divyanshu.com", false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("divyanshu.sonwani@mckinleyrice.co"));
            msg.setSubject("EMail OTP");
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.attachFile(new File("mail.txt"));
            messageBodyPart.setHeader("Content-Type", "text/plain; charset=\"us-ascii\"; name=\"mail.txt\"");
            messageBodyPart.setContent("OTP for email verification- 1234", "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
            return Constants.successMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
