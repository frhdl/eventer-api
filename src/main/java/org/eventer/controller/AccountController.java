package org.eventer.controller;

import org.eventer.entity.Account;
import org.eventer.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(value = "name", required = false) Optional<String> accountName){

            if (accountName.isPresent()) {
                List<Account> accounts = accountService.findAccountByName(accountName.get());

                if (accounts.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return ResponseEntity.ok(accounts);
            }

            List<Account> accounts = accountService.getAllAccounts();

            if (accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(accounts);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id){
        Optional<Account> account = accountService.getAccountById(id);

        if (account.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(account.get());
    }

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account accountToBeCreated) {
        Account account = accountService.createAccount(accountToBeCreated);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account dataToBeUpdated) {
        Optional<Account> account = accountService.updateAccount(id, dataToBeUpdated);

        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(account.get());
    }
}
