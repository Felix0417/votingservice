package ru.felix.votingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.felix.votingservice.AbstractServiceTest;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.testdata.VoteTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.felix.votingservice.testdata.RestaurantTestData.*;
import static ru.felix.votingservice.testdata.UserTestData.*;
import static ru.felix.votingservice.testdata.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

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
        Vote created = service.create(GUEST_ID, RESTAURANT2_ID);
        int newId = created.id();
        Vote newVote = new Vote(guestVote);
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(GUEST_ID), newVote);
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
}