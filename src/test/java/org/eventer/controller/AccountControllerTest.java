package org.eventer.controller;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testCreateAccount() throws Exception {
        Account account = new Account("Test");

        String uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path("/api/account")
                .build()
                .toString();

        final ResponseEntity<Account> response =
            restTemplate.postForEntity(
                    uri,
                    account,
                    Account.class
            );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Account savedAccount = response.getBody();

        Assertions.assertNotNull(savedAccount);
        Assertions.assertNotNull(savedAccount.getId());
        Assertions.assertEquals(account.getName(), savedAccount.getName());
    }

    @Test
    @Order(2)
    public void testCreateEventToAccount() throws Exception {
        Date now = new Date();
        Event event = new Event("Test Started", now, null);
        Event[] events = {event};

        String uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path("/api/event?accountId=1")
                .build()
                .toString();

        final ResponseEntity<Event[]> response =
                restTemplate.postForEntity(
                        uri,
                        events,
                        Event[].class
                );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Event[] savedEvents = response.getBody();

        Assertions.assertNotNull(savedEvents);
        Assertions.assertArrayEquals(events, savedEvents);
    }

    @Test
    @Order(3)
    public void testGetAllEventByAccount() throws Exception {
        int eventsCount = 1;

        String uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path("/api/event?accountId=1")
                .build()
                .toString();

        final ResponseEntity<Event[]> response = restTemplate.getForEntity(uri, Event[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Event[] savedEvents = response.getBody();

        Assertions.assertNotNull(savedEvents);
        Assertions.assertEquals(eventsCount, savedEvents.length);
    }
}
