package ru.felix.votingservice.service;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.felix.votingservice.AbstractServiceTest;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.User;
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
        Vote vote = service.get(VoteTestData.VOTE1_ID);
        vote.setUser((User) Hibernate.unproxy(vote.getUser()));
        VOTE_MATCHER.assertMatch(vote, userVote);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(VOTE_NOT_FOUND_ID));
    }

    @Test
    void create() {
        Vote created = service.create(guest, RESTAURANT2_ID);
        int newId = created.id();
        Vote newVote = new Vote(guestVote);
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(GUEST_ID), newVote);
    }

    @Test
    void createDuplicate() {
        assertThrows(IllegalRequestDataException.class, () -> service.create(user, RESTAURANT1_ID));
    }

    @Test
    void update() {
        VoteTestData.setUpClock(TODAY_BEFORE_ELEVEN);
        Vote updated = updated();
        service.update(USER_ID, RESTAURANT4_ID);
        Vote expectedVote = service.get(USER_ID);
        expectedVote.setUser((User) Hibernate.unproxy(expectedVote.getUser()));
        VOTE_MATCHER.assertMatch(expectedVote, updated);
    }

    @Test
    void updateAfterEleven() {
        VoteTestData.setUpClock(TODAY_AFTER_ELEVEN);
        assertThrows(IllegalRequestDataException.class, () -> service.update(USER_ID, RESTAURANT4_ID));
    }

    @Test
    void updateNotFoundUserId() {
        VoteTestData.setUpClock(TODAY_BEFORE_ELEVEN);
        assertThrows(NotFoundException.class, () -> service.update(NOT_FOUND, RESTAURANT1_ID));
    }

    @Test
    void updateNotFoundRestaurantId() {
        VoteTestData.setUpClock(TODAY_BEFORE_ELEVEN);
        assertThrows(NotFoundException.class, () -> service.update(USER_ID, NOT_FOUND_RESTAURANT_ID));
    }
}