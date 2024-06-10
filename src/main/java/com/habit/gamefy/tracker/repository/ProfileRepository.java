package com.habit.gamefy.tracker.repository;

import com.habit.gamefy.tracker.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsernameAndPassword
            (
                    String username, String password
            );
}
