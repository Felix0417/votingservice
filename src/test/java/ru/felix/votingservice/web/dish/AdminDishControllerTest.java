package ru.felix.votingservice.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.felix.votingservice.model.Dish;
import ru.felix.votingservice.repository.DishRepository;
import ru.felix.votingservice.testdata.DishTestData;
import ru.felix.votingservice.util.JsonUtil;
import ru.felix.votingservice.web.AbstractControllerTest;
import ru.felix.votingservice.testdata.UserTestData;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.felix.votingservice.web.dish.AdminDishController.REST_URL;
import static ru.felix.votingservice.testdata.DishTestData.*;
import static ru.felix.votingservice.testdata.RestaurantTestData.RESTAURANT1_ID;
import static ru.felix.votingservice.testdata.RestaurantTestData.NOT_FOUND_RESTAURANT_ID;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL_WITH_RESTAURANT = REST_URL + "/" + RESTAURANT1_ID + "/dishes";

    @Autowired
    private DishRepository repository;

    @Test
    void create() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_WITH_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)));
        Dish created = DISH_MATCHER.readFromJson(action);
        int newDishId = created.id();
        newDish.setId(newDishId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newDishId), newDish);
    }

    @Test
    void createDuplicate() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL_WITH_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        perform(MockMvcRequestBuilders.post(REST_URL_WITH_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isConflict());
    }

    @Test
    void createNotFoundRestaurant() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + NOT_FOUND_RESTAURANT_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_WITH_RESTAURANT + "/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(repository.getExisted(updated.id()), updated);
    }

    @Test
    void updateWrongId() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_WITH_RESTAURANT + "/" + DISH2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Dish updated = DishTestData.getUpdated();
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL_WITH_RESTAURANT + "/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_WITH_RESTAURANT + "/" + DISH1_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(dish1.id()).isPresent());
    }
}