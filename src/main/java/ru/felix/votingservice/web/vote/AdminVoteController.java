package ru.felix.votingservice.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.felix.votingservice.service.VoteService;

import java.time.LocalDate;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminVoteController {

    static final String REST_URL = "/api/admin/vote";

    private final VoteService service;

    @GetMapping("/winner-from-date")
    public int getWinnerFromDate(@RequestParam LocalDate date) {
        log.info("get winner restaurant id from date - {}", date);
        return service.getWinnerRestaurantIdFromDate(date);
    }

    @GetMapping("/winner-from-current-date")
    public int getWinnerFromCurrentDate() {
        return service.getWinnerRestaurantIdFromDate(LocalDate.now());
    }
}