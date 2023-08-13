package ru.felix.votingservice.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.service.VoteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    private final VoteService service;

    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    @GetMapping
    public List <Vote> getAll(){
        log.info("get all Votes");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id){
        log.info("get Vote by id - {}", id);
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid @RequestBody Vote vote){
        if (vote.isNew()){
            log.info("saving Vote - {}", vote);
            service.create(vote);
        }else{
            log.info("updating Vote - {}", vote);
            service.update(vote);
        }
        ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        log.info("deleting Vote by id - {}", id);
        service.delete(id);
    }
}
