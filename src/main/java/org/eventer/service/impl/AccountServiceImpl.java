package org.eventer.service.impl;

import org.eventer.entity.Account;
import org.eventer.repository.AccountRepository;
import org.eventer.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAccountByName(String accountName) {
        return accountRepository.findAccountByName(accountName);
    }

    @Override
    public Account createAccount(Account accountToBeCreated) {
        return accountRepository.save(accountToBeCreated);
    }

    @Override
    public Optional<Account> updateAccount(Long id, Account dataToBeUpdated) {
        Optional<Account> accountToBeUpdated = accountRepository.findById(id);

        if (accountToBeUpdated.isEmpty()) {
            return Optional.empty();
        }

        Account account = accountToBeUpdated.get();

        // add and update new fields here if necessary
        account.setName(dataToBeUpdated.getName());
        
        return Optional.of(accountRepository.save(account));
    }
}
