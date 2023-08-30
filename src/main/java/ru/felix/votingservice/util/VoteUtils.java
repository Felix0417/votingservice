package ru.felix.votingservice.util;

import ru.felix.votingservice.model.User;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.to.UserTo;
import ru.felix.votingservice.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtils {
    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getUser().id(), vote.getRestaurant().id(), vote.getLocalDate().atStartOfDay());
    }

    public static boolean checkVoteTime(LocalDateTime dateTime) {
        return dateTime.toLocalTime().isBefore(LocalTime.of(11, 0));
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
