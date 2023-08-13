package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.User;
import ru.felix.votingservice.repository.UserRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getAll(){
        return  repository.findAll();
    }

    public User get(int id){
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public User create(User user){
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Transactional
    public User update(User user){
        Assert.notNull(user, "user must not be null");
        return ValidationUtil.checkNotFoundWithId(repository.save(user), user.id());
    }

    public void delete(int id){
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}
