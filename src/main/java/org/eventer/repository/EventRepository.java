package org.eventer.repository;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<List<Event>> findByAccount(Account account);
}
