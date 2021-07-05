package org.eventer.service.impl;


import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.eventer.repository.EventRepository;
import org.eventer.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<List<Event>> findByAccount(Account account) {
        return eventRepository.findByAccount(account);
    }

    @Override
    public List<Event> createEvent(List<Event> eventsToBeCreated) {
        return eventRepository.saveAll(eventsToBeCreated);
    }
}
