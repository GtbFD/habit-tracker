package com.habit.gamefy.tracker.service;

import com.habit.gamefy.tracker.domain.Garden;
import com.habit.gamefy.tracker.domain.Profile;
import com.habit.gamefy.tracker.repository.GardenRepository;
import com.habit.gamefy.tracker.repository.ProfileRepository;
import com.habit.gamefy.tracker.request.GardenRequest;
import com.habit.gamefy.tracker.response.GardenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GardenService {

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public GardenResponse create
            (
                    Long profileId
            ){

        var profileResponse = profileRepository
                .findById(profileId)
                .get();

        var garden = Garden.builder()
                .profile(profileResponse).build();

        var response = gardenRepository.save(garden);

        return GardenResponse.builder()
                .id(response.getId())
                .build();
    }
}
