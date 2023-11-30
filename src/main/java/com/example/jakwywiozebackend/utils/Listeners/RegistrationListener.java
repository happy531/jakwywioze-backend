package com.example.jakwywiozebackend.utils.Listeners;

import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.OnRegistrationCompleteEvent;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

import static com.example.jakwywiozebackend.utils.Utils.BASE_FRONTEND_URL;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;


    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = BASE_FRONTEND_URL + "/users/confirm-registration?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
//        email.setText(confirmationUrl);
//        mailSender.send(email);

        email.setText("<h2>Reset password request </h2>" +
                "<h3> Please click on the button to reset password </h3>"+
                "<a target='_blank' href=" + confirmationUrl + "> <button>Reset your password</button></a>"
        );




        String to = "fimiany@gmail.com";
        //provide sender's email ID
        String from = "jakwywioze@gmail.com";
        //provide Mailtrap's username
        final String username = System.getenv("EMAIL");
        //provide Mailtrap's password
        final String password = System.getenv("PASSWORD");
        //provide Mailtrap's host address
        String host = "smtp.gmail.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object
        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //set email subject field
            message.setSubject("Here comes Jakarta Mail!");
            //set the content of the email message
//            message.setContent("<h2>Register confirmation </h2>" +
//                    "<h3> Please click on the button to confirm registration </h3>"+
//                    "<a target='_blank' href=" + confirmationUrl + "> <button>Confirm registration</button></a>",
//                    "text/html; charset=utf-8"
//            );
            message.setContent("<a class=\"hover-like-lightbox mockup-shadow\" href=\""+ confirmationUrl +"\" rel=\"nofollow\" target=\"_blank\">\n" +
                            "<picture>\n" +
                            "<source srcset=\"https://images01.nicepagecdn.com/page/49/98/html-template-spreview-499811.webp\" type=\"image/webp\" media=\"(max-width: 450px)\">\n" +
                            "<source srcset=\"https://images01.nicepagecdn.com/page/49/98/html-template-spreview-499811.jpg\" type=\"image/jpeg\" media=\"(max-width: 450px)\">\n" +
                            "<source srcset=\"https://images01.nicepagecdn.com/page/49/98/html-template-preview-499811.webp\" type=\"image/webp\">\n" +
                            "<source srcset=\"https://images01.nicepagecdn.com/page/49/98/html-template-preview-499811.jpg\" type=\"image/jpeg\">\n" +
                            "<img alt=\"Text and large button HTML Template\" class=\"media-image img-responsive noversion\" height=\"306\" src=\"https://images01.nicepagecdn.com/page/49/98/html-template-preview-499811.jpg\" style=\"\" width=\"750\">",
                    "text/html; charset=utf-8"
            );

            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}