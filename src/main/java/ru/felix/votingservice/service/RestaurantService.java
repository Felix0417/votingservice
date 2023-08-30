package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository repository;

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return ValidationUtil.checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public ResponseEntity<Restaurant> getWithDishes(int restaurantId) {
        return ResponseEntity.of(repository.getWithDishes(restaurantId));
    }
}
