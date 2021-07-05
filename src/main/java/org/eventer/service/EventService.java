package org.eventer.service;

import org.eventer.entity.Account;
import org.eventer.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> getAllEvents();

    Optional<List<Event>> findByAccount(Account account);

    List<Event> createEvent(List<Event> eventsToBeCreated);
}
