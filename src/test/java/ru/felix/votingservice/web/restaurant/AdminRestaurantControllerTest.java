package ru.felix.votingservice.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.felix.votingservice.model.Restaurant;
import ru.felix.votingservice.repository.RestaurantRepository;
import ru.felix.votingservice.util.JsonUtil;
import ru.felix.votingservice.web.AbstractControllerTest;
import ru.felix.votingservice.web.user.UserTestData;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.felix.votingservice.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.felix.votingservice.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Autowired
    private RestaurantRepository repository;

    @Test
    public void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestaurantController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5));
    }

    @Test
    public void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + restaurant1.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER_WITH_DISHES.contentJson(restaurant1));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)));

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newRestaurantId = created.id();
        newRestaurant.setId(newRestaurantId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(newRestaurantId), newRestaurant);
    }

    @Test
    void wrongCreate() throws Exception {
        Restaurant created = RestaurantTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + restaurant2.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(restaurant1.id()), updated);
    }

    @Test
    void wrongUpdate() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + restaurant2.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + restaurant1.id()))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(restaurant1.id()).isPresent());
    }

    @Test
    public void getWithDishesFromDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + restaurant1.id() + "/from-date").param("localDate", "2020-01-31"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER_WITH_DISHES.contentJson(restaurantWithOldDishes));
    }

    @Test
    public void getWitDishesWithEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + restaurant1.id() + "/from-date")
                .param("localDate", ""))
                .andExpect(status().isUnprocessableEntity());
    }
}