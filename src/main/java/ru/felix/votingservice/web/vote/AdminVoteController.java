package ru.felix.votingservice.web.vote;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.service.VoteService;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.util.VoteUtils;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "api/admin/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminVoteController {

    private final VoteService service;

    private static final Logger log = LoggerFactory.getLogger(AdminVoteController.class);

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("get all Votes");
        return service.getAll().stream().map(VoteUtils::getTo).toList();
    }

    @GetMapping("/winner-from-date")
    public int getWinnerFromDate(LocalDate date) {
        log.info("get winner restaurant id from date - {}", date);
        return service.getWinnerRestaurantIdFromDate(date);
    }

    @GetMapping("/winner-from-current-date")
    public int getWinnerFromCurrentDate() {
        return getWinnerFromDate(LocalDate.now());
    }
}