package com.habit.gamefy.tracker.controller;

import com.habit.gamefy.tracker.request.HabitRequest;
import com.habit.gamefy.tracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habit")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping("/create/profile/{profileId}")
    public ResponseEntity<?> createHabit
            (
                    @RequestBody List<HabitRequest> habits,
                    @PathVariable Long profileId
            ) {

        var response
                = habitService.save(habits, profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        var response
                = habitService.findById(id);

        if (response != null && !response.isDeleted()) {
            return ResponseEntity
                    .status(HttpStatus.OK).body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/all/profile/{profileId}")
    public ResponseEntity<?> findAllByProfileId
            (
                    @PathVariable Long profileId
            ) {
        var response
                = habitService.findAllByProfileId(profileId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById
            (
                    @RequestBody HabitRequest habitRequest,
                    @PathVariable Long id
            ) {

        var response
                = habitService.updateById(habitRequest, id);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        var response = habitService.delete(id);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
