package ru.felix.votingservice.util;

import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.to.VoteTo;

import java.time.LocalTime;

public class VoteUtils {

    private static final LocalTime VALID_TIME = LocalTime.of(11, 0);

    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getUser().id(), vote.getRestaurant().id(), vote.getLocalDate());
    }

    public static boolean isValidTime(LocalTime localTime) {
        return localTime.isAfter(VALID_TIME);
    }
}
