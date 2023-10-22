package com.example.jakwywiozebackend.utils.Events;

import com.example.jakwywiozebackend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationCompleteEvent(
            User user) {
        super(user);

        this.user = user;
    }

}
