package ru.felix.votingservice.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.felix.votingservice.web.AbstractControllerTest;
import ru.felix.votingservice.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.felix.votingservice.web.restaurant.ProfileRestaurantController.REST_URL;
import static ru.felix.votingservice.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
public class ProfileRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    public void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
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
}
