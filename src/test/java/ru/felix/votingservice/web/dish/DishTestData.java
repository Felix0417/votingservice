package ru.felix.votingservice.web.dish;

import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "id");

    public static final Dish dish1 = new Dish(1, "Вареный картофель", LocalDate.now(), 70);
    public static final Dish dish2 = new Dish(2, "Котлета говяжья", LocalDate.now(), 150);
    public static final Dish dish3 = new Dish(3, "Суп рыбный", LocalDate.now(), 120);
    public static final Dish dish4 = new Dish(4, "Водка", LocalDate.now(), 200);

    public static final Dish oldDish1 = new Dish(1, "Курица гриль", LocalDate.of(2020, 1, 31), 270);
    public static final Dish oldDish2 = new Dish(2, "Шаурма", LocalDate.of(2020, 1, 31), 150);
    public static final Dish oldDish3 = new Dish(3, "Сосиска в тесте", LocalDate.of(2020, 1, 31), 40);
    public static final Dish oldDish4 = new Dish(4, "Балтика-7", LocalDate.of(2020, 1, 31), 50);

    public static final List<Dish> dishesFromRestaurant1 = List.of(dish1, dish2, dish3, dish4);

    public static final List<Dish> oldDishesFromRestaurant1 = List.of(oldDish1, oldDish2, oldDish3, oldDish4);

    public static Dish getNew() {
        return new Dish(null, "Новое блюдо", LocalDate.now(), 1000);
    }

    public static Dish getUpdated() {
        return new Dish(dish1.id(), "Обновленное блюдо", dish1.getLocalDate(), 2000);
    }
}
