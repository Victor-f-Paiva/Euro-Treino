package com.paiva.eurotreino.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paiva.eurotreino.model.Macro;
import com.paiva.eurotreino.model.Meso;
import com.paiva.eurotreino.model.Micro;
import com.paiva.eurotreino.model.User;
import com.paiva.eurotreino.model.Workout;
import com.paiva.eurotreino.repository.UserRepository;

@Service
public class TrainingService {

    private UserRepository userRepo;

    public Macro getOrCreateCurrentMacro(Long userId){
        Optional<User> user = userRepo.findById(userId);
        List<Macro> listOfMacro = user.get().getMacroCycles();
        if (listOfMacro.isEmpty()){
            Macro macro = new Macro(LocalDate.now(), new ArrayList<Meso>());
            user.get().getMacroCycles().add(macro);
        }
        return listOfMacro.getLast();
    }

    public Meso getOrCreateCurrentMeso(Macro macro){
        List<Meso> listOfMesos = macro.getMesoCycles();
        if (listOfMesos.isEmpty()){
            Meso meso = new Meso(LocalDate.now(), new ArrayList<Micro>());
            listOfMesos.add(meso);
        }
        return listOfMesos.getLast();
    }

    public Micro getOrCreateCurrentMicro(Meso meso){
        List<Micro> listOfMicros = meso.getMicroCycles();
        if (listOfMicros.isEmpty()){
            Micro micro = new Micro(LocalDate.now(), new ArrayList<Workout>());
            listOfMicros.add(micro);
        }
        return listOfMicros.getLast();
    }

    public void addWorkout (Long userId, Workout workout){
        Macro macro = getOrCreateCurrentMacro(userId);
        Meso meso = getOrCreateCurrentMeso(macro);
        Micro micro = getOrCreateCurrentMicro(meso);
        micro.getWorkouts().add(workout);
    }

}
