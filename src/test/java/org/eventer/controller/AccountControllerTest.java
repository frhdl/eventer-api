package org.eventer.controller;

import org.eventer.entity.Account;
import org.eventer.entity.Event;
import org.eventer.entity.Stats;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.stream.Stream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan
@Configuration
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

        Event event = new Event("New Event", now, null);
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
    }

    @Test
    @Order(3)
    public void testGetAllEventByAccount() throws Exception {
        final int EVENTS_COUNT = 1;

        String uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path("/api/event?accountId=1")
                .build()
                .toString();

        final ResponseEntity<Event[]> response = restTemplate.getForEntity(uri, Event[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Event[] savedEvents = response.getBody();

        Assertions.assertNotNull(savedEvents);
        Assertions.assertEquals(EVENTS_COUNT, savedEvents.length);
    }

    @Test
    @Order(4)
    public void testGetAllStatsByAccount() throws Exception {
        final int STATS_COUNT = 1;

        String uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path("/api/stats?accountId=1")
                .build()
                .toString();

        final ResponseEntity<Stats[]> response = restTemplate.getForEntity(uri, Stats[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Stats[] generatedStats = response.getBody();

        Assertions.assertNotNull(generatedStats);
        Assertions.assertEquals(STATS_COUNT, generatedStats.length);
    }

}
