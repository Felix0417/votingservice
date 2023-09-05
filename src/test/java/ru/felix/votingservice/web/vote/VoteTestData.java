package ru.felix.votingservice.web.vote;

import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.felix.votingservice.web.restaurant.RestaurantTestData.*;
import static ru.felix.votingservice.web.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final String ERR_MSG_DUPLICATE = "You already have vote on this day! Please try it tomorrow!";

    public static final String ERR_MSG_LATE_VOTING = "Your vote is not updated, because you can do it until 11 a.m.";

    public static final VoteTo userVote = new VoteTo(USER_ID, RESTAURANT1_ID, LocalDate.now());
    public static final VoteTo adminVote = new VoteTo(ADMIN_ID, RESTAURANT1_ID, LocalDate.now());
    public static final VoteTo guestVote = new VoteTo(GUEST_ID, RESTAURANT2_ID, LocalDate.now());
    public static final VoteTo userOldVoteTo = new VoteTo(USER_ID, RESTAURANT2_ID, LocalDate.of(2020, 1, 31));

    public static List<VoteTo> getAllTo() {
        return List.of(userVote, adminVote, guestVote, userOldVoteTo);
    }

    public static VoteTo getNew() {
        return new VoteTo(null, RESTAURANT3_ID, null);
    }

    public static VoteTo updated() {
        VoteTo updated = new VoteTo(userVote);
        updated.setRestaurantId(RESTAURANT4_ID);
        return updated;
    }
}
