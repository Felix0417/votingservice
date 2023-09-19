package ru.felix.votingservice.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.User;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.repository.VoteRepository;
import ru.felix.votingservice.util.ClockHolder;
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

    private final EntityManager entityManager;

    private final RestaurantService restaurantService;

    public Vote get(int id) {
        return getFromToday(id).orElseThrow(() -> new NotFoundException("vote was not found"));
    }

    @Transactional
    public Vote create(User user, int restaurantId) {
        if (getFromToday(user.id()).isPresent()) {
            throw new IllegalRequestDataException("You already have vote on this day! Please try it tomorrow!");
        }
        user = entityManager.merge(user);
        Vote vote = new Vote(user, LocalDate.now(), restaurantService.get(restaurantId));
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(int userId, int restaurantId) {
        if (VoteUtils.isValidTime(LocalTime.now(ClockHolder.getClock()))) {
            throw new IllegalRequestDataException("Your vote is not updated, because you can do it until 11 a.m.");
        }
        Vote vote = get(userId);
        Assert.notNull(vote, "vote must not be null");
        vote.setRestaurant(restaurantService.get(restaurantId));
        ValidationUtil.checkNotFoundWithId(voteRepository.save(vote), vote.id());
    }

    private Optional<Vote> getFromToday(int userId) {
        return voteRepository.getByUserId(userId);
    }
}