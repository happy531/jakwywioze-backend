package com.example.jakwywiozebackend.utils.Listeners;

import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.OnRegistrationCompleteEvent;
import com.example.jakwywiozebackend.utils.Utils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

import static com.example.jakwywiozebackend.utils.Utils.BASE_FRONTEND_URL;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;


    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Jakwywioze potwierdzenie rejestracji";
        String confirmationUrl = BASE_FRONTEND_URL + "/users/confirm-registration?token=" + token;

        String from = "jakwywioze.pl";
        final String username = System.getenv("EMAIL");
        final String password = System.getenv("PASSWORD");
        String host = "smtp.gmail.com";
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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));
            message.setSubject(subject);
//            String content = Utils.getMailHtml(
//                    "Dziękujemy za założenie konta na portalu Jakwywioze.pl. Aby potwierdzić rejestrację, kliknij w poniższy przycisk:",
//                    "Jeśli nie zakładałeś konta prosimy o zignorowanie tej wiadomości",
//                    confirmationUrl,
//                    "Potwierdź rejestrację");
            String content = Utils.getMailHtml(
                    "W celu dokończenia procesu rejestracji konta<br> kliknij w poniższy link:",
                    "Jeśli nie zakładałeś konta prosimy o zignorowanie tej wiadomości",
                    confirmationUrl,
                    "Potwierdź rejestrację");
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}