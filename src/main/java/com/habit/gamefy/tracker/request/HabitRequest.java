package com.habit.gamefy.tracker.request;

import lombok.Builder;

@Builder
public record HabitRequest
        (
                String title,
                boolean isRepeatable,
                boolean isPublic,
                boolean notifyMe,
                Long rewardAmount
        ) {
}
