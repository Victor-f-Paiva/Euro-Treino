package com.paiva.eurotreino.service;

import java.util.List;

import com.paiva.eurotreino.exception.NotFoundException;
import com.paiva.eurotreino.model.User;
import com.paiva.eurotreino.repository.UserRepository;

public class UserService {

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
            throw new NotFoundException("User ID \"+id+\" not found. Can't delete.");
        }
    }
}
