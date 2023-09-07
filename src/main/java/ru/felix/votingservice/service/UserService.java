package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.User;
import ru.felix.votingservice.repository.UserRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository repository;

    @Cacheable
    public List<User> getAll() {
        return repository.findAll();
    }

    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public User update(User user) {
        Assert.notNull(user, "user must not be null");
        return ValidationUtil.checkNotFoundWithId(repository.save(user), user.id());
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }
}
