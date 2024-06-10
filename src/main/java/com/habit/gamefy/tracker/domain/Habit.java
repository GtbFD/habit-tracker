package com.habit.gamefy.tracker.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isRepeatable;
    private boolean isPublic;
    private boolean notifyMe;
    private boolean isDeleted;
    private String rewardType;
    private Long rewardAmount;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "habit")
    private List<History> histories;
}
