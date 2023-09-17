package ru.felix.votingservice.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.felix.votingservice.testdata.UserTestData;
import ru.felix.votingservice.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.felix.votingservice.testdata.RestaurantTestData.RESTAURANT1_ID;
import static ru.felix.votingservice.testdata.RestaurantTestData.RESTAURANT2_ID;
import static ru.felix.votingservice.testdata.VoteTestData.ERR_MSG_NOT_FOUND_FROM_DATE;
import static ru.felix.votingservice.web.vote.AdminVoteController.REST_URL;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_WINNER_FROM_DATE = REST_URL + "/winner-from-date";

    private static final String REST_URL_TODAY_WINNER = REST_URL + "/winner-from-current-date";

    @Test
    void getWinnerFromDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_WINNER_FROM_DATE)
                .param("date", "2020-01-31"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(RESTAURANT2_ID)));
    }

    @Test
    void getWinnerFromWrongDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_WINNER_FROM_DATE)
                .param("date", "2001-01-01"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value(ERR_MSG_NOT_FOUND_FROM_DATE));
    }

    @Test
    void getWinnerFromCurrentDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_TODAY_WINNER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(RESTAURANT1_ID)));
    }
}