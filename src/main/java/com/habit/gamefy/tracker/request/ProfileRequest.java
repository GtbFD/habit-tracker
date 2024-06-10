package com.habit.gamefy.tracker.request;

import lombok.Builder;

@Builder
public record ProfileRequest
        (
                Long id,
                String name,
                String lastname,
                String email,
                String username,
                String password,
                String urlPhoto
        ) {

}
