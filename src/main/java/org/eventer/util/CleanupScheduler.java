package org.eventer.util;

import org.eventer.entity.Event;
import org.eventer.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class CleanupScheduler {

    final EventService eventService;

    private static final Logger log = LoggerFactory.getLogger(CleanupScheduler.class);

    private static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");

    public CleanupScheduler(EventService eventService) {
        this.eventService = eventService;
    }

    @Scheduled(cron = "0 0 0 L * *")
    public void cleanupOldEvents() {
        List<Event> events = eventService.getAllEvents();
        List<Event> eventsToBeDeleted = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for(Event event : events){

            long days = ChronoUnit.DAYS.between(event.getCreatedAt().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), today);

            if (days > 1) {
                eventsToBeDeleted.add(event);
            }
        }

        Optional<Exception> result = eventService.deleteAllEvents(eventsToBeDeleted);

        if (result.isEmpty()) {
            log.info("Finished cleaning, items removed:" + eventsToBeDeleted.size());
        }
    }

}
