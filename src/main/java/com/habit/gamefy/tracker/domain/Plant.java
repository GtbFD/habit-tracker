package com.habit.gamefy.tracker.domain;

import com.habit.gamefy.tracker.enums.PlantType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PlantType type;
    private String urlImage;
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "garden_plants",
            joinColumns = {
                    @JoinColumn(name = "plant_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "garden_id")
            }
    )
    private List<Garden> gardens;
}
