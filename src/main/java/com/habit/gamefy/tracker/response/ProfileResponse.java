package com.habit.gamefy.tracker.response;

import lombok.Builder;

@Builder
public record ProfileResponse
        (
                Long id,
                String name,
                String lastname,
                String email,
                String urlPhoto,
                Long resource,
                String resourceType,
                boolean isDeleted
        ) {

}
