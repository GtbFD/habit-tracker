package com.habit.gamefy.tracker.controller;

import com.habit.gamefy.tracker.request.HistoryRequest;
import com.habit.gamefy.tracker.request.HistoryRequestUpdate;
import com.habit.gamefy.tracker.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("/create/habit/{habitId}")
    public ResponseEntity<?> create(@RequestBody HistoryRequest historyRequest,
                                    @PathVariable Long habitId) {
        var response = historyService.create(historyRequest, habitId);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        var response = historyService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/all/habit/{habitId}")
    public ResponseEntity<?> findAllHabitsHistory
            (
                    @PathVariable Long habitId
            ) {

        var response = historyService.findAllHabitsHistory(habitId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/update/{historyId}")
    public ResponseEntity<?> updateHistory(
            @RequestBody HistoryRequestUpdate historyRequestUpdate,
            @PathVariable Long historyId) {

        var response = historyService.update
                (
                        historyRequestUpdate,
                        historyId
                );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete
            (
                    @PathVariable Long id
            ){

        var response = historyService.delete(id);

        if (response != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
