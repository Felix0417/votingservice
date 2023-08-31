package ru.felix.votingservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import java.util.Set;


@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Nullable
    private Set<Dish> dishes;
}
