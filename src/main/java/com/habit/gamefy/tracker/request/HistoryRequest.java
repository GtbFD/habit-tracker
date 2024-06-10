package com.habit.gamefy.tracker.request;

import com.habit.gamefy.tracker.domain.Habit;

import java.time.LocalDate;

public record HistoryRequest
        (
                Habit habit,
                LocalDate madeIn
        ) {
}
