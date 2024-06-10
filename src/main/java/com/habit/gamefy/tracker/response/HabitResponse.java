package com.habit.gamefy.tracker.response;

import com.habit.gamefy.tracker.domain.Profile;
import lombok.Builder;

@Builder
public record HabitResponse
        (
                Long id,
                String title,
                boolean isRepeatable,
                boolean isPublic,
                boolean notifyMe,
                Long rewardAmount,
                boolean isDeleted
        ) {
}
