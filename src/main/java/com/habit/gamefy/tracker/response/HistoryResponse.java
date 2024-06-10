package com.habit.gamefy.tracker.response;

import com.habit.gamefy.tracker.domain.Habit;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HistoryResponse
        (
                Long id,
                //Habit habit,
                LocalDate madeIn
        ) {


}
