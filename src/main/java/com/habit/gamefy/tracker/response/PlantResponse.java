package com.habit.gamefy.tracker.response;

import com.habit.gamefy.tracker.enums.PlantType;
import lombok.Builder;

@Builder
public record PlantResponse
        (
                Long id,
                String name,
                PlantType type
        ) {
}
