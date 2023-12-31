package ru.felix.votingservice.testdata;

import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.util.ClockHolder;
import ru.felix.votingservice.util.VoteUtils;
import ru.felix.votingservice.web.MatcherFactory;

import java.time.*;

import static ru.felix.votingservice.testdata.RestaurantTestData.*;
import static ru.felix.votingservice.testdata.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class,
            "user.password", "user.registered", "restaurant.dishes");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final String ERR_MSG_DUPLICATE = "You already have vote on this day! Please try it tomorrow!";

    public static final String ERR_MSG_LATE_VOTING = "Your vote is not updated, because you can do it until 11 a.m.";

    public static final LocalDateTime TODAY_BEFORE_ELEVEN = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30));

    public static final LocalDateTime TODAY_AFTER_ELEVEN = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 5));

    public final static int VOTE1_ID = 1;

    public final static int VOTE2_ID = 2;

    public final static int VOTE3_ID = 3;

    public final static int VOTE4_ID = 4;

    public final static int VOTE_NOT_FOUND_ID = Integer.MAX_VALUE;

    public static final Vote userVote = new Vote(VOTE1_ID, user, LocalDate.now(), restaurant1);
    public static final Vote adminVote = new Vote(VOTE2_ID, admin, LocalDate.now(), restaurant1);
    public static final Vote guestVote = new Vote(VOTE3_ID, guest, LocalDate.now(), restaurant2);
    public static final Vote userOldVote = new Vote(VOTE4_ID, user, LocalDate.of(2020, 1, 31), restaurant2);

    public static final VoteTo userVoteTo = VoteUtils.getTo(userVote);
    public static final VoteTo adminVoteTo = VoteUtils.getTo(adminVote);
    public static final VoteTo guestVoteTo = VoteUtils.getTo(guestVote);
    public static final VoteTo userOldVoteTo = VoteUtils.getTo(userOldVote);

    public static VoteTo getNewTo() {
        return new VoteTo(null, RESTAURANT3_ID, null);
    }

    public static Vote updated() {
        Vote updated = new Vote(userVote);
        updated.setRestaurant(restaurant4);
        return updated;
    }

    public static VoteTo updatedTo() {
        VoteTo updated = new VoteTo(userVoteTo);
        updated.setRestaurantId(RESTAURANT4_ID);
        return updated;
    }

    public static void setUpClock(LocalDateTime localDateTime) {
        final Clock fixed = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        ClockHolder.setClock(fixed);
    }
}