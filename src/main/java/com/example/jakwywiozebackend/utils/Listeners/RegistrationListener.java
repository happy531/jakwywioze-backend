package com.example.jakwywiozebackend.utils.Listeners;

import com.example.jakwywiozebackend.entity.User;
import com.example.jakwywiozebackend.service.UserService;
import com.example.jakwywiozebackend.utils.Events.OnRegistrationCompleteEvent;
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
        String confirmationUrl = BASE_URL + "/users/confirm-registration?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        mailSender.send(email);
    }
}