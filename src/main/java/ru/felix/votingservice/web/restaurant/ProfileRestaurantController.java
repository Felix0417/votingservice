package ru.felix.votingservice.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.felix.votingservice.service.RestaurantService;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/profile/restaurants";

    public ProfileRestaurantController(RestaurantService service) {
        super(service);
    }
}
