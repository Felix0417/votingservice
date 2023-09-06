package ru.felix.votingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.felix.votingservice.AbstractServiceTest;
import ru.felix.votingservice.error.IllegalRequestDataException;
import ru.felix.votingservice.error.NotFoundException;
import ru.felix.votingservice.model.Restaurant;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.felix.votingservice.testdata.RestaurantTestData.*;


class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(service.getAll(), restaurants);
    }

    @Test
    void get() {
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_RESTAURANT_ID));
    }

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), created);
    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated.id(), updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), updated);
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> service.update(RESTAURANT1_ID, null));
    }

    @Test
    void updateNotSameId() {
        assertThrows(IllegalRequestDataException.class, () -> service.update(NOT_FOUND_RESTAURANT_ID, getUpdated()));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_RESTAURANT_ID));
    }

    @Test
    void getWithDishes() {
        RESTAURANT_MATCHER_WITH_DISHES.assertMatch(service.getWithDishes(RESTAURANT1_ID, LocalDate.now()).getBody(), restaurant1);
    }

    @Test
    void getWithDishesOnEmptyDate() {
        RESTAURANT_MATCHER.assertMatch(service.getWithDishes(RESTAURANT1_ID, null).getBody(), restaurant1);
    }
}