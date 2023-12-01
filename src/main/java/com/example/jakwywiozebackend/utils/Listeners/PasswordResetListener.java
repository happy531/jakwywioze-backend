package com.example.jakwywiozebackend.utils.Listeners;

import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.PasswordResetEvent;
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
public class PasswordResetListener implements
        ApplicationListener<PasswordResetEvent> {

    private final UserService userService;


    @Override
    public void onApplicationEvent(@NonNull PasswordResetEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(PasswordResetEvent event) {
        User user = userService.findUserByEmail(event.getEmail());
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        String recipientAddress = event.getEmail();
        String subject = "Resetowanie hasła";
        String confirmationUrl = BASE_FRONTEND_URL + "/users/password-reset-confirmation?token=" + token;

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
            String content = Utils.getMailHtml(
                    "W celu zresetowania hasła kliknij w przycisk poniżej:",
                    "Jeśli nie resetowałeś hasła prosimy o kontakt na mail z którego dostałeś tą wiadomość",
                    confirmationUrl);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}