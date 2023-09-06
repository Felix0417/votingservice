package ru.felix.votingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.felix.votingservice.AbstractServiceTest;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.testdata.DishTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.felix.votingservice.testdata.DishTestData.*;
import static ru.felix.votingservice.testdata.RestaurantTestData.NOT_FOUND_RESTAURANT_ID;
import static ru.felix.votingservice.testdata.RestaurantTestData.RESTAURANT1_ID;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void getByRestaurant() {
        DISH_MATCHER.assertMatch(service.getByRestaurant(RESTAURANT1_ID, DISH1_ID), dish1);
    }

    @Test
    void getByRestaurantIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByRestaurant(DISH_NOT_FOUND_ID, DISH1_ID));
    }

    @Test
    void getByRestaurantDishIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByRestaurant(RESTAURANT1_ID, DISH_NOT_FOUND_ID));
    }

    @Test
    void create() {
        Dish created = service.create(RESTAURANT1_ID, DishTestData.getNew());
        int newId = created.id();
        Dish newDish = DishTestData.getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.getByRestaurant(RESTAURANT1_ID, newId), newDish);
    }

    @Test
    void createWithNullDish() {
        assertThrows(IllegalArgumentException.class, () -> service.create(RESTAURANT1_ID, null));
    }

    @Test
    void createWithNotFoundRestaurant() {
        assertThrows(NotFoundException.class, () -> service.create(NOT_FOUND_RESTAURANT_ID, dish1));
    }

    @Test
    void update() {
        Dish updated = DishTestData.getUpdated();
        service.update(RESTAURANT1_ID, DISH1_ID, updated);
        DISH_MATCHER.assertMatch(service.getByRestaurant(RESTAURANT1_ID, DISH1_ID), updated);
    }

    @Test
    void updateWrongDishId() {
        Dish updated = DishTestData.getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> service.update(RESTAURANT1_ID, DISH_NOT_FOUND_ID, updated));
    }

    @Test
    void updateWrongDish() {
        Dish updated = DishTestData.getUpdated();
        updated.setId(DISH_NOT_FOUND_ID);
        assertThrows(IllegalRequestDataException.class, () -> service.update(RESTAURANT1_ID, DISH1_ID, updated));
    }

    @Test
    void updateNullDish() {
        assertThrows(IllegalArgumentException.class, () -> service.update(RESTAURANT1_ID, DISH_NOT_FOUND_ID, null));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID, DISH1_ID);
        assertThrows(NotFoundException.class, () -> service.getByRestaurant(RESTAURANT1_ID, DISH1_ID));
    }

    @Test
    void deleteNotFoundRestaurant() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_RESTAURANT_ID, DISH1_ID));
    }

    @Test
    void deleteNotFoundDish() {
        assertThrows(NotFoundException.class, () -> service.delete(RESTAURANT1_ID, DISH_NOT_FOUND_ID));
    }
}