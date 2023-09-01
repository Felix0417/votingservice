package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
        return ValidationUtil.checkNotFoundWithId(voteRepository.getByIdOnCurrentDate(id, LocalDate.now()), id);
    }

    @Transactional
    public void create(int userId, int restaurantId) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (VoteUtils.checkVoteTime(dateTime)) {
            Vote vote = new Vote(getUser(userId), dateTime.toLocalDate(), getRestaurant(restaurantId));
            voteRepository.save(vote);
        }
        // todo throw ex
    }

    @Transactional
    public void update(int userId, int restaurantId) {
        Vote vote = get(userId);
        Assert.notNull(vote, "vote must not be null");
        LocalDateTime dateTime = LocalDateTime.now();
        if (VoteUtils.checkVoteTime(dateTime)) {
            vote.setRestaurant(getRestaurant(restaurantId));
            ValidationUtil.checkNotFoundWithId(voteRepository.save(vote), vote.id());
        }
        // todo throw ex
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
}
