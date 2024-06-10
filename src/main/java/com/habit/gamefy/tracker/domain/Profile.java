package com.habit.gamefy.tracker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String urlPhoto;
    private Long resourceAmount;
    private String resourceType;
    private LocalDate registeredIn;
    private LocalDate lastLogin;
    private boolean isAdmin;
    private boolean isDeleted;

    @OneToOne(mappedBy = "profile")
    private Garden garden;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Habit> habits;
}