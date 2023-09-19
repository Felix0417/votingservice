package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {

    private final RestaurantRepository repository;

    @Cacheable
    public List<Restaurant> getAll() {
        return repository.getAllWithDishes();
    }

    public Restaurant get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return ValidationUtil.checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    //    https://www.educative.io/answers/what-is-the-optionalorelsethrow-method-in-java
    public Restaurant getWithDishes(int restaurantId, LocalDate localDate) {
        return repository.getWithDishes(restaurantId, localDate)
                .orElseThrow(() -> new NotFoundException("This restaurant was not found"));
    }
}
