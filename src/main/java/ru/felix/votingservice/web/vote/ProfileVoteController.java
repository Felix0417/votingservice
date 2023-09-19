package ru.felix.votingservice.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.service.VoteService;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.util.VoteUtils;
import ru.felix.votingservice.web.AuthUser;

@RestController
@RequestMapping(path = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProfileVoteController {

    static final String REST_URL = "/api/profile/vote";

    private final VoteService service;

    @GetMapping
    public Integer get() {
        int userId = AuthUser.authId();
        log.info("get Vote by id - {}", userId);
        return service.get(userId).getId();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser user, @RequestBody VoteTo voteTo) {
        log.info("saving new vote from user - {} and restaurant - {}", user, voteTo.getRestaurantId());
        VoteTo newVoteto = VoteUtils.getTo(service.create(user.getUser(), voteTo.getRestaurantId()));
        return ResponseEntity.ok().body(newVoteto);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser user, @RequestBody VoteTo voteTo) {
        log.info("updating vote from user - {} and restaurant - {}", user.id(), voteTo.getRestaurantId());
        service.update(user.id(), voteTo.getRestaurantId());
    }
}