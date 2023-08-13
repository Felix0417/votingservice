package ru.felix.votingservice.to;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.felix.votingservice.model.Dish;

import java.util.Set;

public class RestaurantTo extends NamedTo {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @JsonManagedReference
    private Set<Dish> dishes;

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
