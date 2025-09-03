package com.paiva.eurotreino.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.service.ExerciseService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "/exercises")
@AllArgsConstructor
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Exercise>> findAll(){
        return ResponseEntity.ok().body(exerciseService.findAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> findById(@PathVariable Long id){
        Exercise obj = exerciseService.findExerciseById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise){
        Exercise obj = exerciseService.creatExercise(exercise);
        return ResponseEntity.created(
            UriComponentsBuilder
            .fromPath("/{id}")
            .buildAndExpand(obj.getId())
            .toUri()
        )
        .body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> update(@PathVariable Long id, @RequestBody Exercise exercise) {
        Exercise obj = exerciseService.updateExercise(id, exercise);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
