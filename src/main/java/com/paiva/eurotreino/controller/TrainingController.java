package com.paiva.eurotreino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paiva.eurotreino.model.Workout;
import com.paiva.eurotreino.service.TrainingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "training")
@AllArgsConstructor
public class TrainingController {

    private TrainingService trainingService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addWorkout(@PathVariable Long userId, @RequestBody Workout workout){
        trainingService.addWorkout(userId, workout);
        return ResponseEntity.noContent().build();
    }

}
