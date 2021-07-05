package org.eventer.controller;

import org.eventer.entity.Account;
import org.eventer.entity.Stats;
import org.eventer.service.AccountService;
import org.eventer.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StatsController {

    private final StatsService statsService;
    private final AccountService accountService;

    public StatsController(StatsService statsService, AccountService accountService) {
        this.statsService = statsService;
        this.accountService = accountService;
    }

    @GetMapping("/stats")
    public ResponseEntity<List<Stats>> getAllStats(@RequestParam(value = "accountId", required = false)Optional<Long> accountId){
        if(accountId.isPresent()) {
            Optional<Account> account = accountService.getAccountById(accountId.get());

            if(account.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Optional<List<Stats>> stats = statsService.findByAccount(account.get());

            if (stats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(stats.get());
        }

        List<Stats> stats = statsService.getAllStats();

        if (stats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(stats);
    }
}
