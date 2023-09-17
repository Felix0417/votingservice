package ru.felix.votingservice.testdata;

import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "localDate");

    public static final String INVALID_DATE_OF_CREATE_DISH = "Sorry, your dish must be on today or in future days";

    public static final int DISH1_ID = 1;

    public static final int DISH2_ID = 2;

    public static final int DISH3_ID = 3;

    public static final int DISH4_ID = 4;

    public static final int DISH5_ID = 5;

    public static final int DISH6_ID = 6;

    public static final int DISH7_ID = 7;

    public static final int DISH8_ID = 8;

    public static final int DISH_NOT_FOUND_ID = Integer.MAX_VALUE;

    public static final Dish dish1 = new Dish(DISH1_ID, "Вареный картофель", LocalDate.now(), 70);

    public static final Dish dish2 = new Dish(DISH2_ID, "Котлета говяжья", LocalDate.now(), 150);

    public static final Dish dish3 = new Dish(DISH3_ID, "Суп рыбный", LocalDate.now(), 120);

    public static final Dish dish4 = new Dish(DISH4_ID, "Водка", LocalDate.now(), 200);

    public static final Dish dish5 = new Dish(DISH5_ID, "Клецки", LocalDate.now(), 150);

    public static final Dish dish6 = new Dish(DISH6_ID, "Борщ с пампушками", LocalDate.now(), 280);

    public static final Dish dish7 = new Dish(DISH7_ID, "Котлета по-киевски", LocalDate.now(), 180);

    public static final Dish dish8 = new Dish(DISH8_ID, "Горилка", LocalDate.now(), 250);

    public static final Dish oldDish1 = new Dish(DISH1_ID + 20, "Курица гриль", LocalDate.of(2020, 1, 31), 270);

    public static final Dish oldDish2 = new Dish(DISH2_ID + 20, "Шаурма", LocalDate.of(2020, 1, 31), 150);

    public static final Dish oldDish3 = new Dish(DISH3_ID + 20, "Сосиска в тесте", LocalDate.of(2020, 1, 31), 40);

    public static final Dish oldDish4 = new Dish(DISH4_ID + 20, "Балтика-7", LocalDate.of(2020, 1, 31), 50);

    public static final List<Dish> dishesFromRestaurant1 = List.of(dish1, dish2, dish3, dish4);

    public static final List<Dish> dishesFromRestaurant2 = List.of(dish5, dish6, dish7, dish8);

    public static final List<Dish> oldDishesFromRestaurant1 = List.of(oldDish1, oldDish2, oldDish3, oldDish4);

    public static Dish getNew() {
        return new Dish(null, "Новое блюдо", LocalDate.now(), 1000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Обновленное блюдо", dish1.getLocalDate(), 2000);
    }
}