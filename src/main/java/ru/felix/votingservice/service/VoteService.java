package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.Vote;
import ru.felix.votingservice.repository.VoteRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository repository;

    public List<Vote> getAll(){
        return repository.findAll();
    }

    public Vote get(int id){
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public Vote create(Vote vote){
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    @Transactional
    public Vote update(Vote vote){
        Assert.notNull(vote, "vote must not be null");
        return ValidationUtil.checkNotFoundWithId(repository.save(vote), vote.id());
    }

    public void delete(int id){
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}
