package com.paiva.eurotreino.service;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class TrainingService {

    @Autowired
    private UserRepository userRepo;

    public Macro getOrCreateCurrentMacro(Long userId){
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));

        List<Macro> listOfMacro = user.getMacroCycles();

        if (listOfMacro.isEmpty()){
            Macro macro = new Macro(LocalDate.now(), new ArrayList<Meso>());
            user.addMacro(macro);
            userRepo.save(user);
        }
        return listOfMacro.getLast();
    }

    public Meso getOrCreateCurrentMeso(Macro macro){
        List<Meso> listOfMesos = macro.getMesoCycles();
        if (listOfMesos.isEmpty()){
            Meso meso = new Meso(LocalDate.now(), new ArrayList<Micro>());
            macro.addMeso(meso);
        }
        return listOfMesos.getLast();
    }

    public Micro getOrCreateCurrentMicro(Meso meso){
        List<Micro> listOfMicros = meso.getMicroCycles();
        if (listOfMicros.isEmpty()){
            Micro micro = new Micro(LocalDate.now(), new ArrayList<Workout>());
            meso.addMicro(micro);
        }
        return listOfMicros.getLast();
    }

    public void addWorkout (Long userId, Workout workout){
        User userObj = userRepo.findById(userId)
            .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));

        Macro macro = getOrCreateCurrentMacro(userId);
        Meso meso = getOrCreateCurrentMeso(macro);
        Micro micro = getOrCreateCurrentMicro(meso);
        micro.addWorkout(workout);

        userRepo.save(userObj);
    }

}
