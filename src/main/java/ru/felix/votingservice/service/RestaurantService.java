package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.assureIdConsistent;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository repository;

    public List<Restaurant> getAll() {
        return repository.getAll();
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Restaurant update(int restaurantId, Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        assureIdConsistent(restaurant, restaurantId);
        return ValidationUtil.checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public ResponseEntity<Restaurant> getWithDishes(int restaurantId, LocalDate localDate) {
        return ResponseEntity.of(repository.getWithDishes(restaurantId, localDate != null ? localDate : LocalDate.now()));
    }
}
