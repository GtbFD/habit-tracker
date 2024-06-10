package com.habit.gamefy.tracker.request;

import com.habit.gamefy.tracker.domain.Profile;
import lombok.Builder;

@Builder
public record GardenRequest
        (
                Long id,
                Profile profile
        ) {
}
