package com.habit.gamefy.tracker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "garden_plants",
            joinColumns = {
                    @JoinColumn(name = "garden_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "plant_id")
            }
    )
    private List<Plant> plants;
}
