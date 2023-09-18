package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.repository.VoteRepository;
import ru.felix.votingservice.util.VoteUtils;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;

    private final UserService userService;

    private final RestaurantService restaurantService;

    public Vote get(int id) {
        return ValidationUtil.checkNotFoundWithId(getFromToday(id).orElse(null), id);
    }

    @Transactional
    public Vote create(int userId, int restaurantId) {
        if (getFromToday(userId).isPresent()) {
            throw new IllegalRequestDataException("You already have vote on this day! Please try it tomorrow!");
        }
        Vote vote = new Vote(userService.get(userId), LocalDate.now(), restaurantService.get(restaurantId));
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(int userId, int restaurantId, LocalTime currentTime) {
        if (VoteUtils.checkVoteTime(currentTime)) {
            throw new IllegalRequestDataException("Your vote is not updated, because you can do it until 11 a.m.");
        }
        Vote vote = get(userId);
        Assert.notNull(vote, "vote must not be null");
        vote.setRestaurant(restaurantService.get(restaurantId));
        ValidationUtil.checkNotFoundWithId(voteRepository.save(vote), vote.id());
    }

    private Optional<Vote> getFromToday(int userId) {
        return voteRepository.getByIdOnCurrentDate(userId);
    }
}