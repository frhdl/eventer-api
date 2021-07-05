package org.eventer.service.impl;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.eventer.repository.StatsRepository;
import org.eventer.entity.Stats;
import org.eventer.service.StatsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository accountStatsRepository;

    public StatsServiceImpl(StatsRepository accountStatsRepository) {
        this.accountStatsRepository = accountStatsRepository;
    }


    @Override
    public List<Stats> getAllStats() {
        return accountStatsRepository.findAll();
    }

    @Override
    public Optional<List<Stats>> findByAccount(Account account) {
        return accountStatsRepository.findByAccount(account);
    }

    @Override
    public void createOrUpdateAccountStats(Account account, List<Event> statsToBeCreated) {
        List<Stats> stats = buildStats(account, statsToBeCreated);

        accountStatsRepository.saveAll(stats);
    }

    private List<Stats> buildStats(Account account, List<Event> eventsToGroup) {
        Stream<Event> events = eventsToGroup.stream();
        List<Stats> stats = new ArrayList<>();

        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy");

        // Group by day
        Map<String, List<Event>> dayMap = events
                .collect(Collectors.groupingBy(event -> fmt.format(event.getHappenedAt())));

        // Group by type
        dayMap.forEach((day, eventList) -> {
            Map<String, Long> countMap = eventList.stream()
                    .collect(Collectors.groupingBy(Event::getType, Collectors.counting()));

                // New stat
                countMap.forEach((type, count) -> {
                    Stats stat = new Stats();
                    stat.setAccount(account);
                    stat.setEventType(type);
                    stat.setDay(day);
                    stat.setCount(count);

                    stats.add(stat);
                });
        });

        return stats;
    }
}
