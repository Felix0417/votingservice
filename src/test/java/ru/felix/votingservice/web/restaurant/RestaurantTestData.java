package ru.felix.votingservice.web.restaurant;

import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.web.MatcherFactory;
import ru.felix.votingservice.web.dish.DishTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,  "dishes");

    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_DISHES =
            MatcherFactory.usingAssertions(Restaurant.class,
//                         No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dishes.id", "dishes.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static int FIRST_RESTAURANT_ID = 1;

    public static int SECOND_RESTAURANT_ID = 2;

    public static int THIRD_RESTAURANT_ID = 3;

    public static int FOURTH_RESTAURANT_ID = 4;

    public static int FIFTH_RESTAURANT_ID = 5;

    public static final Restaurant restaurant1 = new Restaurant(FIRST_RESTAURANT_ID, "Яръ");

    public static final Restaurant restaurant2 = new Restaurant(SECOND_RESTAURANT_ID, "Тарас Бульба");

    public static final Restaurant restaurant3 = new Restaurant(THIRD_RESTAURANT_ID, "Мартин Иден");

    public static final Restaurant restaurant4 = new Restaurant(FOURTH_RESTAURANT_ID, "Мимино");

    public static final Restaurant restaurant5 = new Restaurant(FIFTH_RESTAURANT_ID, "Чайхана");

    public static final Restaurant restaurantWithOldDishes = new Restaurant(FIRST_RESTAURANT_ID, "Яръ");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5);


    static {
        restaurant1.setDishes(DishTestData.dishesFromRestaurant1);
        restaurantWithOldDishes.setDishes(DishTestData.oldDishesFromRestaurant1);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Новый ресторан");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(restaurant1.id(), "Обновленный Ресторан");
    }
}
