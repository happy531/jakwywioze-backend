package com.example.jakwywiozebackend.utils.Listeners;

import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.PasswordResetEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.example.jakwywiozebackend.utils.Utils.BASE_URL;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements
        ApplicationListener<PasswordResetEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;


    @Override
    public void onApplicationEvent(@NonNull PasswordResetEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(PasswordResetEvent event) {
        User user = userService.findUserByEmail(event.getEmail());
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        String recipientAddress = event.getEmail();
        String subject = "Password reset";
        String confirmationUrl = BASE_URL + "/users/password-reset-confirmation?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        mailSender.send(email);
    }
}