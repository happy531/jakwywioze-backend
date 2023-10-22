package com.example.jakwywiozebackend.utils.Events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetEvent extends ApplicationEvent {
    private String email;

    public PasswordResetEvent(String email) {
        super(email);

        this.email = email;
    }

}
