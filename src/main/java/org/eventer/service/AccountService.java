package org.eventer.service;

import org.eventer.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService{

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    List<Account> findAccountByName(String accountName);

    Account createAccount(Account accountToBeCreated);

    Optional<Account> updateAccount(Long id, Account dataToBeUpdated);
}
