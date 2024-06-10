package com.habit.gamefy.tracker.service;

import com.habit.gamefy.tracker.domain.Habit;
import com.habit.gamefy.tracker.repository.HabitRepository;
import com.habit.gamefy.tracker.repository.ProfileRepository;
import com.habit.gamefy.tracker.request.HabitRequest;
import com.habit.gamefy.tracker.response.HabitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<HabitResponse> save
            (
                    List<HabitRequest> habitsRequest,
                    Long profileId
            ) {

        var retrievedProfile
                = profileRepository.findById(profileId).get();

        var savedHabits = new ArrayList<HabitResponse>();

        for (HabitRequest habitRequest : habitsRequest) {
            var newHabit = Habit.builder()
                    .title(habitRequest.title())
                    .isRepeatable(false)
                    .isPublic(false)
                    .notifyMe(false)
                    .rewardAmount(habitRequest.rewardAmount())
                    .profile(retrievedProfile)
                    .build();

            var savedHabit = habitRepository.save(newHabit);

            var responseHabit = HabitResponse.builder()
                    .id(savedHabit.getId())
                    .title(savedHabit.getTitle())
                    .isRepeatable(savedHabit.isRepeatable())
                    .isPublic(savedHabit.isPublic())
                    .notifyMe(savedHabit.isNotifyMe())
                    .rewardAmount(savedHabit.getRewardAmount())
                    .build();

            savedHabits.add(responseHabit);
        }

        return savedHabits;
    }

    public HabitResponse findById
            (
                    Long id
            ) {
        var retrievedHabit
                = habitRepository.findById(id);

        return retrievedHabit.map(habit -> new HabitResponse(
                habit.getId(),
                habit.getTitle(),
                habit.isRepeatable(),
                habit.isPublic(),
                habit.isNotifyMe(),
                habit.getRewardAmount(),
                habit.isDeleted()
        )).orElse(null);
    }

    public List<HabitResponse> findAllByProfileId
            (
                    Long id
            ) {
        var retrievedHabits
                = habitRepository.findAllByProfileId(id);

        var responseHabits = new ArrayList<HabitResponse>();

        if (retrievedHabits.size() != 0) {
            for (Habit habit : retrievedHabits) {
                if (!habit.isDeleted()) {
                    var habitResponse
                            = HabitResponse.builder()
                            .id(habit.getId())
                            .title(habit.getTitle())
                            .isRepeatable(habit.isRepeatable())
                            .isPublic(habit.isPublic())
                            .isDeleted(habit.isDeleted())
                            .notifyMe(habit.isNotifyMe())
                            .rewardAmount(habit.getRewardAmount())
                            .build();

                    responseHabits.add(habitResponse);
                }
            }

            return responseHabits;
        }

        return responseHabits;
    }

    public HabitResponse updateById
            (
                    HabitRequest habitRequest,
                    Long id
            ) {

        var retrievedHabit
                = habitRepository.findById(id);

        if (retrievedHabit.isPresent()
                && !retrievedHabit.get().isDeleted()) {

            var newHabit = Habit.builder()
                    .id(retrievedHabit.get().getId())
                    .title(habitRequest.title())
                    .profile(retrievedHabit.get().getProfile())
                    .isRepeatable(false)
                    .isPublic(false)
                    .notifyMe(false)
                    .rewardAmount(habitRequest.rewardAmount())
                    .build();

            var responseToController
                    = habitRepository.save(newHabit);

            return HabitResponse.builder()
                    .id(responseToController.getId())
                    .title(responseToController.getTitle())
                    .isRepeatable(responseToController.isRepeatable())
                    .isPublic(responseToController.isPublic())
                    .notifyMe(responseToController.isNotifyMe())
                    .rewardAmount(responseToController.getRewardAmount())
                    .isDeleted(responseToController.isDeleted())
                    .build();
        }

        return null;
    }

    public HabitResponse delete
            (
                    Long id
            ) {

        var retrievedHabit
                = habitRepository.findById(id);

        if (retrievedHabit.isPresent()
                && !retrievedHabit.get().isDeleted()) {

            var deletedHabit = Habit.builder()
                    .id(retrievedHabit.get().getId())
                    .title(retrievedHabit.get().getTitle())
                    .isRepeatable(retrievedHabit.get().isRepeatable())
                    .profile(retrievedHabit.get().getProfile())
                    .isPublic(retrievedHabit.get().isPublic())
                    .rewardAmount(retrievedHabit.get().getRewardAmount())
                    .notifyMe(retrievedHabit.get().isNotifyMe())
                    .isDeleted(true)
                    .build();

            habitRepository.save(deletedHabit);

            return HabitResponse.builder()
                    .id(deletedHabit.getId())
                    .title(deletedHabit.getTitle())
                    .isRepeatable(deletedHabit.isRepeatable())
                    .isPublic(deletedHabit.isPublic())
                    .notifyMe(deletedHabit.isNotifyMe())
                    .rewardAmount(deletedHabit.getRewardAmount())
                    .isDeleted(deletedHabit.isDeleted())
                    .build();
        }

        return null;
    }

}
