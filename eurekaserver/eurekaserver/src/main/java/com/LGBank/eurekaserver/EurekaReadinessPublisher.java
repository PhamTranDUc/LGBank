package com.LGBank.eurekaserver;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Component
public class EurekaReadinessPublisher
        implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationEventPublisher publisher;

    public EurekaReadinessPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        AvailabilityChangeEvent.publish(
                publisher,
                event.getApplicationContext(),
                ReadinessState.ACCEPTING_TRAFFIC
        );
    }
}