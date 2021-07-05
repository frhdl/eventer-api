package org.eventer.controller;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.eventer.service.AccountService;
import org.eventer.service.EventService;
import org.eventer.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final AccountService accountService;
    private final StatsService statsService;

    EventController(EventService eventService, AccountService accountService, StatsService statsService) {
        this.eventService = eventService;
        this.accountService = accountService;
        this.statsService = statsService;
    }

    @GetMapping("/event")
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(value = "accountId", required = false) Optional<Long> accountId){
        if(accountId.isPresent()) {
            Optional<Account> account = accountService.getAccountById(accountId.get());

            if (account.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Optional<List<Event>> events = eventService.findByAccount(account.get());

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(events.get());
        }

        List<Event> events = eventService.getAllEvents();

        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(events);
    }

    @PostMapping("/event")
    public ResponseEntity<List<Event>> createEvent(@RequestParam(value = "accountId") Long accountId, @RequestBody List<Event> eventsToBeCreated) {
        Optional<Account> account = accountService.getAccountById(accountId);

        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (Event event: eventsToBeCreated) {
            event.setAccount(account.get());
        }

        List<Event> events = eventService.createEvent(eventsToBeCreated);

        statsService.createOrUpdateAccountStats(account.get(), eventsToBeCreated);

        return ResponseEntity.ok(events);
    }

}
