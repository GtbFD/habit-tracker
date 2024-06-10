package com.habit.gamefy.tracker.service;

import com.habit.gamefy.tracker.domain.Plant;
import com.habit.gamefy.tracker.repository.GardenRepository;
import com.habit.gamefy.tracker.repository.PlantRepository;
import com.habit.gamefy.tracker.request.PlantRequest;
import com.habit.gamefy.tracker.response.GardenResponse;
import com.habit.gamefy.tracker.response.PlantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private GardenRepository gardenRepository;

    public PlantResponse create
            (
                    PlantRequest plantRequest
            ) {

        var plant = Plant.builder()
                .name(plantRequest.name())
                .type(plantRequest.type())
                .build();

        var response = plantRepository.save(plant);

        return PlantResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .type(response.getType())
                .build();
    }

    public List<PlantResponse> addPlantInGarden
            (
                    List<Long> plantsId,
                    Long gardenId
            ) {

        var garden
                = gardenRepository.findById(gardenId);

        var reveitedPlants = new ArrayList<Plant>();
        var responsePlants = new ArrayList<PlantResponse>();

        if (garden.isPresent()) {
            for (Long plantId : plantsId) {
                var retreivedPlant =
                        plantRepository.findById(plantId);

                if (retreivedPlant.isPresent()
                        && !retreivedPlant.get().isDeleted()) {
                    reveitedPlants.add(retreivedPlant.get());

                    var responsePlant =
                            PlantResponse.builder()
                                    .id(retreivedPlant
                                            .get().getId())
                                    .name(retreivedPlant
                                            .get().getName())
                                    .type(retreivedPlant
                                            .get().getType())
                                    .build();

                    responsePlants.add(responsePlant);
                }
            }
        }
        garden.get()
                .getPlants().addAll(reveitedPlants);

        var response
                = gardenRepository.save(garden.get());

        return responsePlants;
    }

    public List<PlantResponse> getAllPlantsByGardenId
            (
                    Long gardenId
            ) {

        var plants
                = gardenRepository.findById(gardenId)
                .get().getPlants();

        var response = new ArrayList<PlantResponse>();

        for (Plant plant : plants) {
            if (!plant.isDeleted()) {
                response.add(
                        PlantResponse.builder()
                                .id(plant.getId())
                                .name(plant.getName())
                                .type(plant.getType())
                                .build()
                );
            }
        }

        return response;
    }

    public PlantResponse update
            (
                    Long id,
                    PlantRequest plantRequest
            ) {
        var retrievedPlant
                = plantRepository.findById(id);

        var responseToController
                = PlantResponse.builder();

        if (retrievedPlant.isPresent()
                && !retrievedPlant.get().isDeleted()) {
            retrievedPlant.get().setId(id);
            retrievedPlant.get().setName(plantRequest.name());
            retrievedPlant.get().setType(plantRequest.type());

            var response
                    = plantRepository.save(retrievedPlant.get());

            responseToController
                    .id(response.getId())
                    .name(response.getName())
                    .type(response.getType());
        }

        return responseToController.build();
    }

    public PlantResponse delete
            (
                    Long id
            ) {
        var plant
                = plantRepository.findById(id);

        if (plant.isPresent()
                && !plant.get().isDeleted()) {
            plant.get().setDeleted(true);
            plantRepository.save(plant.get());
        }

        return PlantResponse.builder()
                .id(plant.get().getId())
                .name(plant.get().getName())
                .type(plant.get().getType())
                .build();
    }

    public PlantResponse findById
            (
                    Long id
            ) {
        var plant
                = plantRepository.findById(id);

        if (plant.isPresent()
                && !plant.get().isDeleted()) {
            return PlantResponse.builder()
                    .id(plant.get().getId())
                    .name(plant.get().getName())
                    .type(plant.get().getType())
                    .build();
        }
        return null;
    }
}
