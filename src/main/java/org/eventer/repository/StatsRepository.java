package org.eventer.repository;

import org.eventer.entity.Account;
import org.eventer.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    Optional<List<Stats>> findByAccount(Account account);
}
