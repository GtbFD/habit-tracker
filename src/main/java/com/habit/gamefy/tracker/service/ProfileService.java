package com.habit.gamefy.tracker.service;

import com.habit.gamefy.tracker.domain.Profile;
import com.habit.gamefy.tracker.request.ProfileRequest;
import com.habit.gamefy.tracker.response.ProfileResponse;
import com.habit.gamefy.tracker.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HabitService habitService;

    public ProfileResponse save
            (
                    ProfileRequest profileRequest
            ) {

        var profile = Profile.builder()
                .name(profileRequest.name())
                .lastname(profileRequest.lastname())
                .email(profileRequest.email())
                .username(profileRequest.username())
                .password(profileRequest.password())
                .resourceAmount(0L)
                .registeredIn(LocalDate.now())
                .isAdmin(false)
                .isDeleted(false)
                .build();

        var profileResponse
                = profileRepository.save(profile);

        return new ProfileResponse(
                profileResponse.getId(),
                profileResponse.getName(),
                profileResponse.getLastname(),
                profileResponse.getEmail(),
                profileResponse.getUrlPhoto(),
                profileResponse.getResourceAmount(),
                profileResponse.getResourceType(),
                profileResponse.isDeleted()
        );
    }

    public ProfileResponse findById
            (
                    Long id
            ) {
        var retreivedProfile
                = profileRepository.findById(id).get();

        return new ProfileResponse(
                retreivedProfile.getId(),
                retreivedProfile.getName(),
                retreivedProfile.getLastname(),
                retreivedProfile.getEmail(),
                retreivedProfile.getUrlPhoto(),
                retreivedProfile.getResourceAmount(),
                retreivedProfile.getResourceType(),
                retreivedProfile.isDeleted()
        );
    }

    public ProfileResponse auth
            (
                    String username,
                    String password
            ) {
        var responseAuth =
                profileRepository
                        .findByUsernameAndPassword(username, password);

        if (responseAuth.isPresent()) {

            var responseToController = new ProfileResponse(
                    responseAuth.get().getId(),
                    responseAuth.get().getName(),
                    responseAuth.get().getLastname(),
                    responseAuth.get().getEmail(),
                    responseAuth.get().getUrlPhoto(),
                    responseAuth.get().getResourceAmount(),
                    responseAuth.get().getResourceType(),
                    responseAuth.get().isDeleted()
            );

            return responseToController;
        }

        return null;
    }

    public ProfileResponse update
            (
                    Long id,
                    Profile profile
            ) {
        Profile retrievedProfile
                = profileRepository.findById(id).get();

        var updateProfile = Profile.builder()
                .id(retrievedProfile.getId())
                .name(retrievedProfile.getName())
                .lastname(retrievedProfile.getLastname())
                .email(retrievedProfile.getEmail())
                .username(retrievedProfile.getUsername())
                .password(retrievedProfile.getPassword())
                .urlPhoto(profile.getUrlPhoto())
                .resourceAmount(retrievedProfile.getResourceAmount())
                .registeredIn(retrievedProfile.getRegisteredIn())
                .lastLogin(retrievedProfile.getLastLogin())
                .isAdmin(retrievedProfile.isAdmin())
                .isDeleted(retrievedProfile.isDeleted())
                .build();

        var responseUpdate
                = profileRepository.save(updateProfile);

        var responseToController = new ProfileResponse(
                responseUpdate.getId(),
                responseUpdate.getName(),
                responseUpdate.getLastname(),
                responseUpdate.getEmail(),
                responseUpdate.getUrlPhoto(),
                responseUpdate.getResourceAmount(),
                responseUpdate.getResourceType(),
                responseUpdate.isDeleted()
        );

        return responseToController;
    }


    public boolean delete
            (
                    Long id
            ) {
        var retrievedProfile
                = profileRepository.findById(id).get();

        var updateProfile = Profile.builder()
                .id(retrievedProfile.getId())
                .name(retrievedProfile.getName())
                .lastname(retrievedProfile.getLastname())
                .email(retrievedProfile.getEmail())
                .urlPhoto(retrievedProfile.getUrlPhoto())
                .resourceAmount(retrievedProfile.getResourceAmount())
                .registeredIn(retrievedProfile.getRegisteredIn())
                .lastLogin(retrievedProfile.getLastLogin())
                .isAdmin(retrievedProfile.isAdmin())
                .isDeleted(true)
                .build();

        var responseToController
                = profileRepository.save(updateProfile);

        return responseToController.isDeleted();
    }

    public boolean giveRewardAmount
            (
                    Long id,
                    Long habitId
            ){
        var habit = habitService.findById(habitId);

        var profile = profileRepository.findById(id);

        if (profile.isPresent()){
            profile.get().setResourceAmount(profile.get().getResourceAmount()
                    + habit.rewardAmount());

            profileRepository.save(profile.get());

            return true;
        }
        return false;
    }
}
