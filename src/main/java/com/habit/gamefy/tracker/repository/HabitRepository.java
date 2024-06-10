package com.habit.gamefy.tracker.repository;

import com.habit.gamefy.tracker.domain.Habit;
import com.habit.gamefy.tracker.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    public List<Habit> findAllByProfileId(Long id);

}
