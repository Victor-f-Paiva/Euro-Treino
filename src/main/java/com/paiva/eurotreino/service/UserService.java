package com.paiva.eurotreino.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.Macro;
import com.paiva.eurotreino.model.Meso;
import com.paiva.eurotreino.model.Micro;
import com.paiva.eurotreino.model.User;
import com.paiva.eurotreino.model.Workout;
import com.paiva.eurotreino.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User findById(Long id){
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found. ID "+id));
    }

    public User creatUser(User objUser){
        return userRepo.save(objUser);
    }

    public User updatUser(Long id, User objUser){
        try{
            User updatedUser = findById(id);
            updatedUser.setEmail(objUser.getEmail());
            updatedUser.setName(objUser.getName());
            return userRepo.save(updatedUser);
        } catch(RuntimeException exception) {
            throw new NotFoundException("User ID "+id+" not found. Can't update.");
        }
    }

    public void deleteUser(long id){
        try {
            User deletUser = findById(id);
            userRepo.delete(deletUser);
        } catch(RuntimeException exception){
            throw new NotFoundException("User ID "+id+" not found. Can't delete.");
        }
    }

    public Workout visualizeWorkout(Long id){
        User user = findById(id);
        if (user.getMacroCycles().isEmpty()){
            throw new NotFoundException("User "+ id + " has no Macro found.");
        }

        Macro macro = user.getMacroCycles().getLast();
        if (macro.getMesoCycles().isEmpty()){
            throw new NotFoundException("User "+ id + " has no Meso found.");
        }

        Meso meso = macro.getMesoCycles().getLast();
        if (meso.getMicroCycles().isEmpty()){
            throw new NotFoundException("User "+ id + " has no Micro found.");
        }

        Micro micro = meso.getMicroCycles().getLast();
        if (micro.getWorkouts().isEmpty()){
            throw new NotFoundException("User "+ id + " has no workout found.");
        }

        return micro.getWorkouts().getLast();
    }

    public double getWorkoutVolume(Workout workout){
        return workout.getTotalVolume();
    }
    
}
