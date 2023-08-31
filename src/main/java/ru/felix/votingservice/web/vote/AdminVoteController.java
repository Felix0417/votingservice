package ru.felix.votingservice.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.felix.votingservice.service.VoteService;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.util.VoteUtils;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminVoteController {

    static final String REST_URL = "api/admin/vote";

    private final VoteService service;

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