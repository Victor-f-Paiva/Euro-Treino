package com.paiva.eurotreino.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.Exercise;
import com.paiva.eurotreino.repository.ExerciseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepo;

    public List<Exercise> findAllExercises(){
        return exerciseRepo.findAll();
    }

    public Exercise findExerciseById(Long id){
        return exerciseRepo.findById(id).orElseThrow(() -> new NotFoundException("Exercise not found. ID "+ id));
    }

    public Exercise creatExercise(Exercise exercise){
        return exerciseRepo.save(exercise);
    }

    public Exercise updateExercise(Long id, Exercise exercise){
        try {
            Exercise updatedExercise = findExerciseById(id);
            updatedExercise.setName(exercise.getName());
            updatedExercise.setPrimaryGroup(exercise.getPrimaryGroup());
            updatedExercise.setSecondaryGroup(exercise.getSecondaryGroup());
            return exerciseRepo.save(updatedExercise);

        } catch (RuntimeException e) {
            throw new NotFoundException("Exercise to update not found. ID "+ id);
        }
    }

    public void deleteExercise(Long id){
        try {
            exerciseRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new NotFoundException("User ID "+ id+ " not found. Can't delete.");
        }
    }

}
