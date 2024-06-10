package com.habit.gamefy.tracker.repository;

import com.habit.gamefy.tracker.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    public List<History> findAllByHabitId(Long id);

}
