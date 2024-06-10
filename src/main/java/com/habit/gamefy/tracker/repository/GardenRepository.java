package com.habit.gamefy.tracker.repository;

import com.habit.gamefy.tracker.domain.Garden;
import com.habit.gamefy.tracker.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GardenRepository
        extends JpaRepository<Garden, Long> {
}
