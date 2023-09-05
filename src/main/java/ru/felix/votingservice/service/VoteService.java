package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.model.User;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.repository.UserRepository;
import ru.felix.votingservice.repository.VoteRepository;
import ru.felix.votingservice.util.VoteUtils;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public Vote get(int id) {
        return ValidationUtil.checkNotFoundWithId(getFromToday(id).orElse(null), id);
    }

    @Transactional
    public Vote create(int userId, int restaurantId) {
        if (getFromToday(userId).isPresent()) {
            throw new IllegalRequestDataException("You already have vote on this day! Please try it tomorrow!");
        }
        Vote vote = new Vote(getUser(userId), LocalDate.now(), getRestaurant(restaurantId));
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(int userId, int restaurantId) {
        Vote vote = get(userId);
        Assert.notNull(vote, "vote must not be null");
        if (VoteUtils.checkVoteTime(LocalDateTime.now())) {
            throw new IllegalRequestDataException("Your vote is not registered, because you can do it before 11 a.m.");
        }
        vote.setRestaurant(getRestaurant(restaurantId));
        ValidationUtil.checkNotFoundWithId(voteRepository.save(vote), vote.id());
    }

    public int getWinnerRestaurantIdFromDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return ValidationUtil.checkNotFound(voteRepository.getWinnerFromDate(date), "date " + date);
    }

    @Transactional
    public void deleteFromCurrentDate(int userId) {
        voteRepository.delete(get(userId));
    }

    public User getUser(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        Assert.notNull(user, "user must not be null");
        return user;
    }

    public Restaurant getRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurant;
    }

    private Optional<Vote> getFromToday(int userId) {
        return voteRepository.getByIdOnCurrentDate(userId, LocalDate.now());
    }
}
