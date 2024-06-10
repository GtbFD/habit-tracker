package com.habit.gamefy.tracker.controller;

import com.habit.gamefy.tracker.request.PlantRequest;
import com.habit.gamefy.tracker.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @PostMapping("/create")
    public ResponseEntity<?> create
            (
                    @RequestBody PlantRequest plantRequest
            ) {

        var response
                = plantService.create(plantRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/add/garden/{gardenId}")
    public ResponseEntity<?> addPlantsInGarden
            (
                    @RequestBody List<Long> plantsId,
                    @PathVariable Long gardenId
            ) {

        var response
                = plantService.addPlantInGarden(plantsId, gardenId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all/garden/{gardenId}")
    public ResponseEntity<?> getAllPlantsByGardenId
            (
                    @PathVariable Long gardenId
            ) {
        var response
                = plantService.getAllPlantsByGardenId(gardenId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById
            (
                    @PathVariable Long id
            ) {
        var response
                = plantService.findById(id);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update
            (
                    @PathVariable Long id,
                    @RequestBody PlantRequest plantRequest
            ) {
        var response
                = plantService.update(id, plantRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete
            (
                    @PathVariable Long id
            ) {
        var response
                = plantService.delete(id);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
