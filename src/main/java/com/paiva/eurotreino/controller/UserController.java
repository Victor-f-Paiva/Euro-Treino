package com.paiva.eurotreino.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.paiva.eurotreino.model.User;
import com.paiva.eurotreino.model.Workout;
import com.paiva.eurotreino.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User obj = userService.creatUser(user);
        return ResponseEntity.created(
            UriComponentsBuilder
            .fromPath("/{id}")
            .buildAndExpand(obj.getId())
            .toUri()
        )
        .body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        User objUser = userService.updatUser(id, user);
        return ResponseEntity.ok().body(objUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/workout/{id}")
    public ResponseEntity<Workout> visualizeWorkout(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.visualizeWorkout(id));
    }
    
}
