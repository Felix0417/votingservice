package ru.felix.votingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.DishRepository;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.felix.votingservice.util.validation.ValidationUtil.assureIdConsistent;
import static ru.felix.votingservice.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository dishRepository;

    private final RestaurantRepository restaurantRepository;

    public Dish getByRestaurant(int restaurantId, int dishId) {
        return checkNotFoundWithId(dishRepository.findByRestaurantIdAndId(restaurantId, dishId).orElse(null), dishId);
    }

    public List<Dish> getAllByRestaurantFromDate(int restaurantId, LocalDate localDate) {
        return dishRepository.getAllByRestaurantFromDate(restaurantId, localDate);
    }

    @Transactional
    public Dish create(int restaurantId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        if (dish.getLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalRequestDataException("Sorry, your dish must be on today or in future days");
        }
        Restaurant restaurant =
                ValidationUtil.checkNotFoundWithId(restaurantRepository.findById(restaurantId)
                        .orElse(null), restaurantId);
        dish.setRestaurant(restaurant);
        dish.setLocalDate(dish.getLocalDate());
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(int restaurantId, int dishId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        assureIdConsistent(dish, dishId);
        Dish updateDish = getByRestaurant(restaurantId, dishId);
        updateDish.setPrice(dish.getPrice());
        updateDish.setName(dish.getName());
        dishRepository.save(updateDish);
    }

    public void delete(int restaurantId, int dishId) {
        checkNotFoundWithId(dishRepository.deleteByRestaurantIdAndId(restaurantId, dishId) != 0, dishId);
    }

}
