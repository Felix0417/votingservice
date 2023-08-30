package ru.felix.votingservice.web.vote;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.service.VoteService;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.web.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/profile/vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {

    private final VoteService service;

    private static final Logger log = LoggerFactory.getLogger(ProfileVoteController.class);

    @GetMapping
    public int get() {
        int userId = AuthUser.authId();
        log.info("get Vote by id - {}", userId);
        return service.get(userId).getRestaurant().id();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@AuthenticationPrincipal AuthUser user, @RequestBody VoteTo voteTo) {
        log.info("saving new vote from user - {} and restaurant - {}", user, voteTo.getRestaurantId());
        service.create(user.id(), voteTo.getRestaurantId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser user, @RequestBody VoteTo voteTo) {
        log.info("updating vote from user - {} and restaurant - {}", user.id(), voteTo.getRestaurantId());
        service.update(user.id(), voteTo.getRestaurantId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser user) {
        log.info("deleting current vote from user - {}", user);
        service.deleteFromCurrentDate(user.id());
    }
}