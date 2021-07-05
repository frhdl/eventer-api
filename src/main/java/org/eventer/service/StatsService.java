package org.eventer.service;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.eventer.entity.Stats;

import java.util.List;
import java.util.Optional;

public interface StatsService {

    List<Stats> getAllStats();

    Optional<List<Stats>> findByAccount(Account account);

    void createOrUpdateAccountStats(Account account, List<Event> statsToBeCreated);
}
