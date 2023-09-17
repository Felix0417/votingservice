package ru.felix.votingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.felix.votingservice.AbstractServiceTest;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.testdata.VoteTestData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.felix.votingservice.testdata.RestaurantTestData.*;
import static ru.felix.votingservice.testdata.UserTestData.NOT_FOUND;
import static ru.felix.votingservice.testdata.UserTestData.USER_ID;
import static ru.felix.votingservice.testdata.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void getAll() {
        VOTE_MATCHER.assertMatch(service.getAll(), VoteTestData.getAll());
    }

    @Test
    void get() {
        VOTE_MATCHER.assertMatch(service.get(VoteTestData.VOTE1_ID), userVote);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(VOTE_NOT_FOUND_ID));
    }

    @Test
    void create() {
        service.deleteFromCurrentDate(USER_ID);
        Vote created = service.create(USER_ID, RESTAURANT1_ID);
        int newId = created.id();
        Vote newVote = new Vote(userVote);
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(USER_ID), newVote);
    }

    @Test
    void createDuplicate() {
        assertThrows(IllegalRequestDataException.class, () -> service.create(USER_ID, RESTAURANT1_ID));
    }

    @Test
    void update() {
        Vote updated = updated();
        service.update(USER_ID, RESTAURANT4_ID, TIME_BEFORE_ELEVEN);
        VOTE_MATCHER.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void updateAfterEleven() {
        assertThrows(IllegalRequestDataException.class, () -> service.update(USER_ID, RESTAURANT4_ID, TIME_AFTER_ELEVEN));
    }

    @Test
    void updateNotFoundUserId() {
        assertThrows(NotFoundException.class, () -> service.update(NOT_FOUND, RESTAURANT1_ID, TIME_BEFORE_ELEVEN));
    }

    @Test
    void updateNotFoundRestaurantId() {
        assertThrows(NotFoundException.class, () -> service.update(USER_ID, NOT_FOUND_RESTAURANT_ID, TIME_BEFORE_ELEVEN));
    }

    @Test
    void getWinnerRestaurantIdFromDate() {
        assertEquals(1, service.getWinnerRestaurantIdFromDate(LocalDate.now()));
    }

    @Test
    void getWinnerFromWrongDate() {
        assertThrows(NotFoundException.class, () -> service.getWinnerRestaurantIdFromDate(LocalDate.of(2001, 1, 1)));
    }

    @Test
    void getWinnerFromNullDate() {
        assertThrows(IllegalArgumentException.class, () -> service.getWinnerRestaurantIdFromDate(null));
    }

    @Test
    void deleteFromCurrentDate() {
        service.deleteFromCurrentDate(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deleteFromCurrentDateNotFoundId() {
        assertThrows(NotFoundException.class, () -> service.deleteFromCurrentDate(NOT_FOUND));
    }
}