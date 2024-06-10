package com.habit.gamefy.tracker.controller;

import com.habit.gamefy.tracker.request.GardenRequest;
import com.habit.gamefy.tracker.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/garden")
public class GardenController {

    @Autowired
    private GardenService gardenService;

    @PostMapping("/create/profile/{profileId}")
    public ResponseEntity<?> create(@PathVariable Long profileId){
        var response = gardenService.create(profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
