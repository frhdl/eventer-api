package org.eventer.unit.controller;

import org.eventer.controller.AccountController;
import org.eventer.entity.Account;
import org.eventer.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void getAllAccountsWhenGetMethod() throws Exception {
        String accountName = "Test Account";
        Account account = new Account(accountName);

        List<Account> accounts = Arrays.asList(account);

        given(accountService.getAllAccounts()).willReturn(accounts);

        mockMvc.perform(get("/api/account")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(account.getName())));
    }

    @Test
    public void getAccountByIdWhenGetMethod() throws Exception {
        String accountName = "Test Account";
        Account account = new Account(accountName);
        account.setId(1L);

        given(accountService.getAccountById(account.getId())).willReturn(java.util.Optional.of(account));

        mockMvc.perform(get("/api/account/" + account.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(account.getName())));
    }

    @Test
    public void createAccountWhenPostMethod() throws Exception {
        String accountName = "Test Account";
        String payload = String.format("{\"name\": \"%s\"}", accountName);

        Account account = new Account(accountName);

        given(accountService.createAccount(account)).willReturn(account);

        mockMvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(account.getName())));
    }

    @Test
    public void updateAccountWhenPutMethod() throws Exception {
        String accountName = "Test Name";
        String payload = String.format("{\"name\": \"%s\"}", accountName);

        Account account = new Account(accountName);
        account.setId(1L);

        given(accountService.updateAccount(account.getId(), account)).willReturn(java.util.Optional.of(account));

        mockMvc.perform(put("/api/account/" + account.getId().toString())
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(account.getName())));
    }

    // TODO: test findAccountByNameWhenGetMethod
}
