package ru.felix.votingservice.util;

import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtils {
    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getUser().id(), vote.getRestaurant().id(), vote.getLocalDate());
    }

    public static boolean checkVoteTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().isAfter(LocalTime.of(11, 0));
    }
}
