package com.habit.gamefy.tracker.request;

import com.habit.gamefy.tracker.enums.PlantType;
import lombok.Builder;

@Builder
public record PlantRequest
        (
                String name,
                PlantType type
        ){
}
