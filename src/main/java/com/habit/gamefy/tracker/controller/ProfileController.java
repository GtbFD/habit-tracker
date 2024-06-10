package com.habit.gamefy.tracker.controller;

import com.habit.gamefy.tracker.domain.Profile;
import com.habit.gamefy.tracker.request.ProfileRequest;
import com.habit.gamefy.tracker.repository.ProfileRepository;
import com.habit.gamefy.tracker.response.ProfileResponse;
import com.habit.gamefy.tracker.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private ProfileRepository profileRepository;
    private ProfileService profileService;

    @Autowired
    public ProfileController
            (
                    ProfileRepository profileRepository,
                    ProfileService profileService
            ) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProfileById
            (
                    @PathVariable Long id
            ) {

        var response
                = profileService.findById(id);

        if (response.isDeleted()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("This user does not exists!");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth
            (
                    @RequestBody ProfileRequest profileRequest
            ) {

        var retrievedProfile =
                profileService.auth(
                        profileRequest.username(),
                        profileRequest.password()
                );

        if (retrievedProfile != null) {
            if (retrievedProfile.isDeleted()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("You have been banned");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(retrievedProfile);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProfile
            (
                    @RequestBody ProfileRequest profileRequest
            ) {

        var response
                = profileService.save(profileRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile
            (
                    @PathVariable Long id,
                    @RequestBody Profile newProfile
            ) {

        var response
                = profileService.update(id, newProfile);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProfile
            (
                    @PathVariable Long id
            ) {

        var response = profileService.delete(id);

        if (response) {
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @PostMapping("/give/reward/{profileId}/{habitId}")
    public ResponseEntity<?> giveRewardAmount
            (
                    @PathVariable Long profileId,
                    @PathVariable Long habitId
            ){

        var response = profileService.giveRewardAmount(profileId, habitId);

        if (response){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
