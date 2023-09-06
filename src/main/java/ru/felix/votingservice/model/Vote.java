package ru.felix.votingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
@Getter
@Setter
@ToString
//@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity implements Serializable {

    @JoinColumn(name = "user_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "voting_date", nullable = false)
    private LocalDate localDate;

    @JoinColumn(name = "restaurant_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
//    @JsonIgnore
    private Restaurant restaurant;

    public Vote(Integer id, User user, LocalDate localDate, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    public Vote(Vote vote) {
        this(vote.id, vote.user, vote.localDate, vote.restaurant);
    }
}
