package ru.felix.votingservice.web.vote;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.felix.votingservice.repository.VoteRepository;
import ru.felix.votingservice.service.VoteService;
import ru.felix.votingservice.to.VoteTo;
import ru.felix.votingservice.util.JsonUtil;
import ru.felix.votingservice.util.VoteUtils;
import ru.felix.votingservice.web.AbstractControllerTest;
import ru.felix.votingservice.web.user.UserTestData;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.felix.votingservice.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.felix.votingservice.web.vote.ProfileVoteController.REST_URL;
import static ru.felix.votingservice.web.vote.VoteTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
class ProfileVoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository repository;

    @Autowired
    private VoteService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(RESTAURANT1_ID)));
    }

    @Test
    void create() throws Exception {
        service.deleteFromCurrentDate(UserTestData.USER_ID);
        VoteTo newVoteTo = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)));
        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        newVoteTo.setUserId(created.getUserId());
        newVoteTo.setLocalDate(LocalDate.now());
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
    }

    @Test
    void createDuplicate() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.detail").value(ERR_MSG_DUPLICATE));
    }


    @Test
    @EnabledIf("isBeforeEleven")
    void update() throws Exception {
        VoteTo updated = VoteTestData.updated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTo actual = VoteUtils.getTo(repository.getExisted(UserTestData.USER_ID));
        VOTE_TO_MATCHER.assertMatch(actual, updated);
    }

    @Test
    @DisabledIf("isBeforeEleven")
    void wrongUpdate() throws Exception {
        VoteTo updated = VoteTestData.updated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.detail").value(ERR_MSG_LATE_VOTING));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());

        assertFalse(repository.getByIdOnCurrentDate(userVote.getUserId(), LocalDate.now()).isPresent());
    }

    boolean isBeforeEleven() {
        return LocalTime.now().isBefore(LocalTime.of(11, 0));
    }
}