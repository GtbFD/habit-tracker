package com.habit.gamefy.tracker.service;

import com.habit.gamefy.tracker.domain.Habit;
import com.habit.gamefy.tracker.domain.History;
import com.habit.gamefy.tracker.repository.HistoryRepository;
import com.habit.gamefy.tracker.request.HistoryRequest;
import com.habit.gamefy.tracker.request.HistoryRequestUpdate;
import com.habit.gamefy.tracker.response.HabitResponse;
import com.habit.gamefy.tracker.response.HistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private HabitService habitService;

    public HistoryResponse create
            (
                    HistoryRequest historyRequest,
                    Long habitId
            ) {

        var retrievedHabit
                = habitService.findById(habitId);

        var habit = Habit.builder()
                .id(retrievedHabit.id())
                .build();

        var history = History.builder()
                .habit(habit)
                .madeIn(historyRequest.madeIn())
                .build();

        var response
                = historyRepository.save(history);

        return HistoryResponse.builder()
                .id(response.getId())
                //.habit(response.getHabit())
                .madeIn(response.getMadeIn())
                .build();
    }

    public HistoryResponse findById
            (
                    Long id
            ) {

        var response
                = historyRepository.findById(id);

        if (response.isPresent()
                && !response.get().isDeleted()) {
            return HistoryResponse.builder()
                    .id(response.get().getId())
                    .madeIn(response.get().getMadeIn())
                    .build();
        }

        return HistoryResponse.builder()
                .id(null)
                .madeIn(null)
                .build();
    }

    public List<HistoryResponse> findAllHabitsHistory
            (
                    Long habitId
            ) {

        var responseHistories
                = historyRepository.findAllByHabitId(habitId);

        var responseToController
                = new ArrayList<HistoryResponse>();
        for (History history : responseHistories) {
            if (!history.isDeleted()) {
                var historyResponse
                        = HistoryResponse.builder()
                        .id(history.getId())
                        .madeIn(history.getMadeIn())
                        .build();

                responseToController.add(historyResponse);
            }
        }

        Collections.sort(responseToController, (x, y) -> x.madeIn().compareTo(y.madeIn()));

        return responseToController;
    }

    public HistoryResponse update
            (
                    HistoryRequestUpdate historyRequestUpdate,
                    Long historyId
            ) {

        var history = historyRepository.findById(historyId)
                .get();
        if (!history.isDeleted()) {
            history.setMadeIn(historyRequestUpdate.madeIn());
        }
        var responseUpdate
                = historyRepository.save(history);

        var responseToController
                = HistoryResponse.builder()
                .id(responseUpdate.getId())
                .madeIn(responseUpdate.getMadeIn())
                .build();

        return responseToController;
    }

    public HistoryResponse delete
            (
                    Long id
            ) {

        var retrievedHistory = historyRepository
                .findById(id).get();

        if (!retrievedHistory.isDeleted()) {

            retrievedHistory.setDeleted(true);
        }

        var responseToHistory
                = historyRepository.save(retrievedHistory);

        return HistoryResponse.builder()
                .id(responseToHistory.getId())
                .madeIn(responseToHistory.getMadeIn())
                .build();
    }
}
